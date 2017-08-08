package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/T1DMzks/article/details/70198430
 * distinct用于去重， 我们生成的RDD可能有重复的元素，使用distinct方法可以去掉重复的元素, 不过此方法涉及到混洗，操作开销很大
 * */
public class Distinct {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Distinct")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        List<String> slist = new ArrayList<>(6);
        slist.add("cool");
        slist.add("good");
        slist.add("bad");
        slist.add("fine");
        slist.add("cool");
        slist.add("good");
        JavaRDD<String> arr = sc.parallelize(slist).distinct();

        List<String> collect = arr.collect();
        for (String str:collect) {
            System.out.print(str+"   ");
        }


        arr.foreach(s -> System.out.println(s));
    }
}
