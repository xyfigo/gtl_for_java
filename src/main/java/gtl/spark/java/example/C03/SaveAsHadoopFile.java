package gtl.spark.java.example.C03;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/databricks/learning-spark/blob/master/src/main/java/com/oreilly/learningsparkexamples/java/BasicSaveSequenceFile.java
 * http://lxw1234.com/archives/2015/07/404.htm
 * http://lxw1234.com/archives/2015/07/363.htm
 */

public class SaveAsHadoopFile {
    public static class ConvertToWritableTypes implements PairFunction<Tuple2<String, Integer>, Text, IntWritable> {
        public Tuple2<Text, IntWritable> call(Tuple2<String, Integer> record) {
            return new Tuple2(new Text(record._1), new IntWritable(record._2));
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName = "D:\\temp\\model\\SaveAsHadoopFileResult3";
        JavaSparkContext sc = new JavaSparkContext(
                "local[*]", "SaveAsHadoopFile", System.getenv("SPARK_HOME"), System.getenv("JARS"));
        List<Tuple2<String, Integer>> input = new ArrayList();
        input.add(new Tuple2("coffee", 1));
        input.add(new Tuple2("coffee", 2));
        input.add(new Tuple2("pandas", 3));
        JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(input);
        JavaPairRDD<Text, IntWritable> result = rdd.mapToPair(new ConvertToWritableTypes());
        result.saveAsHadoopFile(fileName, Text.class, IntWritable.class, SequenceFileOutputFormat.class);
    }
}
