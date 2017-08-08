package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

/**
 * spark伪集合
 * 尽管 RDD 本身不是严格意义上的集合，但它也支持许多数学上的集合操作，比如合并和相交操作
 */
public class Union {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Union")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> RDD1 = sc.parallelize(Arrays.asList("aa", "aa", "bb", "cc", "dd"));
        JavaRDD<String> RDD2 = sc.parallelize(Arrays.asList("aa", "dd", "ff"));
        JavaRDD<String> unionRDD = RDD1.union(RDD2);
        List<String> collect = unionRDD.collect();
        for (String str : collect) {
            System.out.print(str + "   ");
        }
    }
}
