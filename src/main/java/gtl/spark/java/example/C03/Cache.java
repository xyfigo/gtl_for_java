package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Cache")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        List<String> alist = new ArrayList<String>(6);
        alist.add("abc");
        alist.add("b");
        alist.add("c");
        alist.add("d");
        alist.add("e");
        alist.add("f");
        JavaRDD<String> rdd = sc.parallelize(alist);
        System.out.println(rdd);
        System.out.println("---------------");
        System.out.println(rdd.cache());
        System.out.println("---------------");
        rdd.foreach(s -> System.out.println(s));
    }
}
