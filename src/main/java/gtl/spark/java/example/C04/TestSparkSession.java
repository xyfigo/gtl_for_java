package gtl.spark.java.example.C04;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class TestSparkSession {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .master("local")
                .appName("spark session example")
                .getOrCreate();
        Dataset<Row> df = sparkSession.read().option("header", "false").csv("D:\\devs\\data\\spark\\channel_100_xy.csv");
        df.show();  //只显示前面20条数据
//        for (int i = 0; i < df.count() ; i++) {
//            System.out.print(df.select()+"   ");
//        }
    }
}
