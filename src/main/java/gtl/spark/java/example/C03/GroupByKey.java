package gtl.spark.java.example.C03;

import org.apache.spark.sql.*;
import scala.Tuple2;
import scala.Tuple3;

import java.util.LinkedList;
import java.util.List;


public class GroupByKey {
    public static void main(String[] args) {
        //SparkSession
        SparkSession spark = SparkSession
                .builder()
                .appName("GroupByKeySample")
                .master("local[*]")
                .getOrCreate();

        //input data
        List<Tuple3<Integer, String, String>> DataList = new LinkedList<>();
        DataList.add(new Tuple3<>(1, "a", "a"));
        DataList.add(new Tuple3<>(1, "b", "b"));
        DataList.add(new Tuple3<>(1, "c", "c"));
        DataList.add(new Tuple3<>(2, "a", "a"));
        DataList.add(new Tuple3<>(2, "b", "b"));

        //dataset
        Dataset<Row> dataSet = spark.createDataset(DataList, Encoders.tuple(Encoders.INT(), Encoders.STRING(), Encoders.STRING())).toDF("c1", "c2", "c3");
        dataSet.show();

        /**
         * +---+---+---+
         | c1| c2| c3|
         +---+---+---+
         |  1|  a|  a|
         |  1|  b|  b|
         |  1|  c|  c|
         |  2|  a|  a|
         |  2|  b|  b|
         +---+---+---+
         * */

        //groupBy and aggregate
        Dataset<Row> dataSet1 = dataSet.groupBy("c1").agg(org.apache.spark.sql.functions.collect_list("c2")).toDF("c1", "c2");
        dataSet1.show();

        /**
         * +---+---------+
         | c1|       c2|
         +---+---------+
         |  1|[a, b, c]|
         |  2|   [a, b]|
         +---+---------+
         *
         * */

        //stop
        spark.stop();
    }
}
