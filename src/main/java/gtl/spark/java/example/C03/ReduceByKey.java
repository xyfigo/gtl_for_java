package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


/**
 * Merge the values for each key using an associative and commutative reduce function.
 * This will also perform the merging locally on each mapper before sending results to a reducer, similarly to a "combiner" in MapReduce.
 * Output will be hash-partitioned with the existing partitioner parallelism level.
 */

public class ReduceByKey {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Map")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        //Reduce Function for sum
        Function2<Integer, Integer, Integer> reduceSumFunc = (accum, n) -> (accum + n);   //参考Scala的Funtion2
        // PairRDD parallelized with 3 partitions
        JavaRDD<String> x = sc.parallelize(Arrays.asList("a", "b", "a", "a", "b", "b", "b", "b"),
                3);
        // mapToPair function will map JavaRDD to JavaPairRDD
        JavaPairRDD<String, Integer> rddX = x.mapToPair(e -> new Tuple2<String, Integer>(e, 1));   //设置每个字符串出现次数为1
        // New JavaPairRDD
        JavaPairRDD<String, Integer> rddY = rddX.reduceByKey(reduceSumFunc);  //按照Key重新组合 输出结果集
        //Print tuples
        for (Tuple2<String, Integer> element : rddY.collect()) {
            System.out.println("(" + element._1 + ", " + element._2 + ")");
        }

//        (a, 3)
//        (b, 5)






        JavaRDD<String> lines = sc.textFile("D:\\devs\\data\\spark\\D03\\sample.txt");

        JavaPairRDD<String, Integer> wordPairRDD = lines.flatMapToPair(new PairFlatMapFunction<String, String, Integer>() {
            @Override
            public Iterator<Tuple2<String, Integer>> call(String s) throws Exception {
                ArrayList<Tuple2<String, Integer>> tpLists = new ArrayList<Tuple2<String, Integer>>();
                String[] split = s.split("\\s+");
                for (int i = 0; i < split.length; i++) {
                    Tuple2 tp = new Tuple2<String, Integer>(split[i], 1);
                    tpLists.add(tp);
                }
                return tpLists.iterator();
            }
        });

        JavaPairRDD<String, Integer> wordCountRDD = wordPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) throws Exception {
                return i1 + i2;
            }
        });
        Map<String, Integer> collectAsMap = wordCountRDD.collectAsMap();
        for (String key : collectAsMap.keySet()) {
            System.out.println("(" + key + "," + collectAsMap.get(key) + ")");
        }

        /**(kks,1)
         (ee,6)
         (bb,2)
         (zz,1)
         (ff,1)
         (cc,1)
         (zks,2)
         (dd,2)
         (aa,5)
         * */
    }
}
