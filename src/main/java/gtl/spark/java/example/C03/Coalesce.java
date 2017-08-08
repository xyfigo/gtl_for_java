package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class Coalesce {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Coalesce")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        List<Integer> al = new ArrayList<Integer>(6);
        for (int i = 0; i < 6; ++i)
            al.add(i + 1);
        JavaRDD<Integer> rdd = sc.parallelize(al);
        Integer r = rdd.aggregate(0, (v1, v2) -> v1 > v2 ? v1 : v2, (v1, v2) -> v1 + v2);
        System.out.println(r);
        JavaRDD<Integer> rdd2 = rdd.coalesce(2, true);
        r = rdd2.aggregate(0, (v1, v2) -> v1 > v2 ? v1 : v2, (v1, v2) -> v1 + v2);
        System.out.println(r);
    }
}
