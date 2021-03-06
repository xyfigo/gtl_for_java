package gtl.index.itree;

import gtl.geom.*;
import gtl.index.shape.TriangleShape;
import gtl.io.storage.StorageManager;
import gtl.io.storage.StorageSuits;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.List;

public class DTITreeMainApp {
    public static void main(String [] args) throws IOException{
        SparkSession spark=SparkSession
                .builder()
                .master("local[*]")
                .appName("TITreeTest")
                .getOrCreate();

        StorageManager sm = StorageSuits.createMemoryStorageManager();
        Timeline t = TimelineGenerator.generate();

        //2. calculate the min and max values
        double minTime=TimelineGenerator.timeMinValue;
        double maxTime= TimelineGenerator.timeMaxValue;
        final Triangle triangle = new TriangleShape(
                new Vector2D(minTime-1,maxTime+1),
                new Vector2D(maxTime+1,maxTime+1),
                new Vector2D(minTime-1,minTime-1)
        );

        final TITree<LabeledInterval> tree= new TITree<LabeledInterval>(
                (TriangleShape)triangle.clone(),
                1024,
                sm,
                JavaSparkContext.fromSparkContext(spark.sparkContext())
        );

        //1.load data from file
        System.out.println("begin load data:"+System.currentTimeMillis());
        JavaRDD<LabeledInterval> data = spark
                .sparkContext()
                .textFile(TimelineGenerator.outputFileName,1)
                .toJavaRDD()                                  //JavaRDD<String>
                .map(r-> GeomSuits.createTimeline(r)) //JavaRDD<Timeline>
                .flatMap(r->r.getLabeledIntervals().iterator())//JavaRDD<LabeledInterval>
                .filter(r->triangle.contains(r.getLowerBound(),r.getUpperBound()));

        System.out.println("end load data:"+System.currentTimeMillis());

        System.out.println("begin collect:"+System.currentTimeMillis());
        List<LabeledInterval> list= data.collect();

        System.out.println("end collect:"+System.currentTimeMillis());

        long  time0=System.currentTimeMillis();
        System.out.println("begin insert:"+System.currentTimeMillis());
        int i=0;
        System.out.println("begin insert " + i + " time:" + System.currentTimeMillis());
       for(LabeledInterval li:list){
           tree.insert(li);
           i++;
       }
        System.out.println("end insert " + i + " time:" + System.currentTimeMillis());
        return;
//        data.foreach(r->tree.insert(r));
//        System.out.println("end insert:"+System.currentTimeMillis());
//        // each insertion 2.55ms
//
//        long time1 = System.currentTimeMillis();
//        System.out.println("end query:"+System.currentTimeMillis());
//        for(i=0;i<1000;++i){
//            Timeline timeline = TimelineGenerator.generate();
//            Envelope e = GeomSuits.createEnvelope();
//            QueryShapeGenerator queryShapeGenerator= new QueryShapeGenerator(triangle)  ;
//            for( LabeledInterval li:t.getLabeledIntervals()){
//                e.combine(queryShapeGenerator.overlaps(li).getMBR());
//            }
//            tree.regionQuery(new RegionShape(e));
//        }
//        System.out.println("end query:"+System.currentTimeMillis());
//        System.out.println(time1-time0);
//        System.out.println(System.currentTimeMillis()-time1);
    }
}
