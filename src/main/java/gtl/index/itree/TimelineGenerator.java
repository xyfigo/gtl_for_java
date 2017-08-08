package gtl.index.itree;
import gtl.common.CommonSuits;
import gtl.geom.GeomSuits;
import gtl.geom.LabeledInterval;
import gtl.geom.Timeline;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.random.StandardNormalGenerator;
import org.apache.spark.mllib.random.RandomRDDs;
import org.apache.spark.mllib.random.RandomDataGenerator;
import org.apache.spark.mllib.random.UniformGenerator;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineGenerator {
    static double timeMaxValue=10000;//最大时间值
    static double timeMinValue=0;//最小时间值
    static double timelineMinLength=10;//一条时间线的最小长度
    static double timelineMaxLength=50;//一条时间线的最大长度
    static double intervalMinDuring=2;//间隔最小持续时间
    static double intervalMaxDuring=10;//间隔最大持续时间
    static int     labelTypes=5;//标识种类
    static final long numberTimelines=10000000;// the number of the generated timelines
    static String outputFileName = "d://devs//data//timelines.txt";

    public static void main(String[] args){
        //StandardNormalGenerator sng = new StandardNormalGenerator();
        UniformGenerator ug= new UniformGenerator();
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
            for(long i=0;i<numberTimelines;++i){
                Timeline tl = generate();
                tl.getIdentifier().reset(i);
                bw.write(tl.toString());
                if(i<numberTimelines-1)
                    bw.newLine();
            }
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * generate a timeline
     * @return
     */
    public static Timeline generate(){
        //StandardNormalGenerator sng = new StandardNormalGenerator();
        UniformGenerator ug= new UniformGenerator();

        while(true){
            double timelineLength = ug.nextValue()*(timelineMaxLength-timelineMinLength)+timelineMinLength;
            double timelineStartValue = ug.nextValue()*(timeMaxValue-timeMinValue)+timeMinValue;
            double timelineEndValue = timelineStartValue+timelineLength;
            double intervalStartValue = timelineStartValue;
            double intervalEndValue = intervalStartValue;
            double intervalLength = ug.nextValue()*(intervalMaxDuring-intervalMinDuring);
            int label =0;
            List<LabeledInterval> labeledIntervals = new ArrayList<>();
            do{
                intervalLength = ug.nextValue()*(intervalMaxDuring-intervalMinDuring);
                intervalEndValue = intervalStartValue+intervalLength;
                while (label==0)
                    label= (int)(ug.nextValue()*labelTypes);
                LabeledInterval li = new LabeledInterval(intervalStartValue,
                        intervalEndValue,Integer.valueOf(label).toString(),-1L,-1L);
                labeledIntervals.add(li);
                intervalStartValue=intervalEndValue;
                label=0;
            } while (intervalEndValue<timelineEndValue);

            if(labeledIntervals.size()>0){//generate a timeline
                Timeline tl = GeomSuits.createTimeline(
                        CommonSuits.createIdentifier(-1L),
                        labeledIntervals
                );
                return tl;
            }
        }
    }
}
