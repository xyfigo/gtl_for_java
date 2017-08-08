package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

/**
 * RDD1.intersection(RDD2) 返回两个RDD的交集，并且去重
 * intersection 需要混洗数据，比较浪费性能
 */


public class Intersection {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Intersection")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> RDD1 = sc.parallelize(Arrays.asList("aa", "aa", "bb", "cc", "dd"));
        JavaRDD<String> RDD2 = sc.parallelize(Arrays.asList("aa", "dd", "ff"));
        JavaRDD<String> intersectionRDD = RDD1.intersection(RDD2);
        List<String> collect = intersectionRDD.collect();
        for (String str : collect) {
            System.out.print(str + "    ");
        }
    }
}
