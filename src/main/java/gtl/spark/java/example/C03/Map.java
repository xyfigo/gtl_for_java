package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.Iterator;

/**
 * map() 接收一个函数，把这个函数用于 RDD 中的每个元素，将函数的返回结果作为结果RDD编程
 * RDD 中对应元素的值 map是一对一的关系
 */
public class Map {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("Map")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        //可以是HDFS也可以是本地文件也可以是Hadoop支持的格式，但是注意返回的都是按照行去读取结果值
        JavaRDD<String> lines = sc.textFile("D:\\devs\\data\\spark\\D03\\sample.txt");

        //直接输出结果
        for (Iterator iter = lines.collect().iterator(); iter.hasNext();) {
            System.out.println(iter.next().toString());
        }

        //对数据进行处理以后再进行返回
        JavaRDD<Iterable<String>> mapRDD = lines.map((Function<String, Iterable<String>>) s -> {
            String[] split = s.split("\\s+");
            return Arrays.asList(split);
        });

        //读取第一个元素
        System.out.println(mapRDD.first());
    }
}
