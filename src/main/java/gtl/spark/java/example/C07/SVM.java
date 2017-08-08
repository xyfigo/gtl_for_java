package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;

public class SVM {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\u.txt";

    public static void main(String[] args){
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("SVM")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> data = sc.textFile(DATA_FILE);//获取数据集
        JavaRDD<LabeledPoint> parsedData = data.map(r -> {
            String t[] = r.split("\\|");//“|”是转义字符,必须得加"\\"
            LabeledPoint s = new LabeledPoint(
                    Double.valueOf(t[0]),
                    Vectors.dense( Double.valueOf(t[1])));
            return s;
        }).cache();//转化数据格式
        SVMModel model = SVMWithSGD.train(parsedData.rdd(),10);//训练数据模型
        System.out.println(model.weights());//打印权重
        System.out.println(model.intercept());//打印截距
    }
}
