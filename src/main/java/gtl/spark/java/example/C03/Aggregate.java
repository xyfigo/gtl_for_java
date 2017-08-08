package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/T1DMzks/article/details/70189509
 * https://www.iteblog.com/archives/1512.html
 * <p>
 * 在Spark中创建RDD的创建方式大概可以分为三种：（1）、从集合中创建RDD；（2）、从外部存储创建RDD；（3）、从其他RDD创建。
 * <p>
 * 调用SparkContext 的 parallelize()，将一个存在的集合，变成一个RDD，这种方式试用于学习spark和做一些spark的测试
 * <p>
 * def parallelize[T](list : java.util.List[T], numSlices : scala.Int) : org.apache.spark.api.java.JavaRDD[T] = {}
 * -第一个参数是一个List集合           注意java版本只能接收List集合 Arrays.asList()
 * -第二个参数是一个分区，可以默认
 * -返回的是一个JavaRDD[T]
 * <p>
 * 只有scala版本才有makeRDD,和parallelize的作用是一致的
 * <p>
 * DataFrame有多重读取外部文件的方式，csv等都可以 textfile()、read()等
 * <p>
 * aggregate聚合函数，第一个参数是初始值，第二个参数是partions
 */

public class Aggregate {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("spark://192.168.0.130:7077")
                .appName("Aggregate")
                .getOrCreate();

        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        List<Integer> al = new ArrayList<Integer>(6);
        for (int i = 0; i < 6; ++i)
            al.add(i + 1);

        JavaRDD<Integer> rdd = sc.parallelize(al, 2);
        Integer r = rdd.aggregate(0, (v1, v2) -> v1 > v2 ? v1 : v2, (v1, v2) -> v1 + v2);
        System.out.println(r);
    }
}
