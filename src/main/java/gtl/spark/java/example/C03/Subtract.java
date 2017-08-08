package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

/**
 * RDD1.subtract(RDD2),返回在RDD1中出现，但是不在RDD2中出现的元素，不去重
 */
public class Subtract {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Distinct")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> RDD1 = sc.parallelize(Arrays.asList("aa", "aa", "bb", "cc", "dd"));
        JavaRDD<String> RDD2 = sc.parallelize(Arrays.asList("aa", "dd", "ff"));
        JavaRDD<String> subtractRDD = RDD1.subtract(RDD2);
        List<String> collect = subtractRDD.collect();
        for (String str : collect) {
            System.out.print(str + "    ");
        }
    }
}
