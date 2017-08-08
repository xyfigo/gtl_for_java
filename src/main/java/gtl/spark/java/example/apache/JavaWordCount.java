package gtl.spark.java.example.apache;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount {
    private static final Pattern SPACE = Pattern.compile(" ");
   // private static final String outputDirName = "D:\\temp\\model\\JavaWordCount";

    public static void main(String[] args) throws Exception {

        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("JavaWordCount")
                .getOrCreate();

        JavaRDD<String> lines = spark.read().textFile("D:\\devs\\data\\spark\\D03\\sample.txt").javaRDD();

        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());  //WORDS_EXTRACTOR

        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));  //WORDS_MAPPER

        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> (i1 + i2));  //WORDS_REDUCER

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }

        //counts.saveAsTextFile(outputDirName);

        spark.stop();
    }
}
