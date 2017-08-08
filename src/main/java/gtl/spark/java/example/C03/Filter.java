package gtl.spark.java.example.C03;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://blog.csdn.net/T1DMzks/article/details/70198393

public class Filter {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Filter")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        //filter
        List<Integer> ilist = new ArrayList<Integer>(5);
        for (int i = 0; i < 5; i++)
            ilist.add(i + 1);
        JavaRDD<Integer> arr = sc.parallelize(ilist);        //创建数据集
        JavaRDD<Integer> result = arr.filter(v -> v >= 3);    //进行筛选工作
        result.foreach(s -> System.out.println(s));


        JavaRDD<String> lines = sc.textFile("D:\\devs\\data\\spark\\D03\\sample.txt");
        JavaRDD<String> zksRDD = lines.filter((Function<String, Boolean>) s -> s.contains("zks"));  //按行读取数据
        List<String> zksCollect = zksRDD.collect();
        for (String str : zksCollect) {
            System.out.println(str);
        }

    }
}
