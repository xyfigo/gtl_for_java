package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;   //元组 http://www.yiibai.com/scala/scala_tuples.html

/**
 * scala是没有mapToPair函数的，scala版本只需要map就可以了
 */

public class MapToPair {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("MapToPair")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> lines = sc.textFile("D:\\devs\\data\\spark\\D03\\sample.txt");
        //输入的是一个string的字符串，输出的是一个(String, Integer)的map
        JavaPairRDD<String, Integer> pairRDD = lines.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s.split("\\s+")[0], 1);  //Tuple最多可以是22   这里的value都是1
            }
        });
        pairRDD.foreach(s -> System.out.println(s));
    }
}
