package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.Iterator;


/**
 * http://blog.csdn.net/T1DMzks/article/details/70198393
 * FlatMap和Map区别    http://blog.csdn.net/samhacker/article/details/41927567
 *
 * 总结：
 * <p>
 * - Spark 中 map函数会对每一条输入进行指定的操作，然后为每一条输入返回一个对象；
 * <p>
 * - 而flatMap函数则是两个操作的集合——正是“先映射后扁平化”：
 * <p>
 * 操作1：同map函数一样：对每一条输入进行指定的操作，然后为每一条输入返回一个对象
 * <p>
 * 操作2：最后将所有对象合并为一个对象
 */

public class FlatMap {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("FlatMap")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> lines = sc.textFile("D:\\devs\\data\\spark\\D03\\sample.txt");
        JavaRDD<String> flatMapRDD = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                String[] split = s.split("\\s+");
                return Arrays.asList(split).iterator(); //spark2.0以上，对flatMap的方法有所修改，就是flatMap中的Iterator和Iteratable的小区别
            }
        });
        //读取第一个元素
        System.out.println(flatMapRDD.first());  //aa
    }
}
