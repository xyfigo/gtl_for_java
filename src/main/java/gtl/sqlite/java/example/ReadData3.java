package gtl.sqlite.java.example;

import gtl.io.File;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

public class ReadData3 {
    private static String data = "D:\\devs\\studies\\scala\\HelloWorld\\Re";

    public static void main(String[] args) throws FileNotFoundException {
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("ReadData3")
                .getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
        JavaRDD<String> Traindata = sc.textFile(data);//获取数据集
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Trainresult.txt"), true));
        writer.println("90 158 434");
        writer.println("1");
        writer.println("DD");
        for (Iterator<String> iter = Traindata.collect().iterator(); iter.hasNext(); ) {
            writer.append(iter.next().toString());
            writer.println();
        }
        writer.close();
    }
}
