package gtl.spark.java.example;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class WordCount {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("WordCount")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        /*wc.txt
        good bad cool
        hadoop spark mllib
        good spark mllib
        cool spark bad
         */
        JavaRDD<String> rdd = sc.textFile("d:\\devs\\data\\spark\\wc.txt");
        //对每行中的字符串以空格分割，形成新集合，也就是说通过flatMap变换后
        //RDD由四行变成12行,也就是12个单词
        JavaRDD<String> words = rdd.flatMap(s -> Arrays.asList(Pattern.compile(" ").split(s)).iterator());

        //def mapToPair[K2, V2](f :  PairFunction[T, K2, V2]) :  JavaPairRDD[K2, V2]
        // public interface PairFunction<T, K, V> extends Serializable {
        //    Tuple2<K, V> call(T var1) throws Exception;
        //}
        //将这12个单词变成12个Tuple2<String,Integer>对象
        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<String, Integer>(s, 1));
        //def reduceByKey(func : Function2[V, V, V]) : JavaPairRDD[K, V]
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
    }
}
