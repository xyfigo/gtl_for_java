package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;

public class Bayes {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\bayes.txt";

    static double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Bayes")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> datai = sc.textFile(DATA_FILE);//读取数据集
        JavaRDD<LabeledPoint> data = datai.map(r -> {
            String[] u = r.split(",");
            String[] t = u[1].split(" ");
            LabeledPoint s = new LabeledPoint(
                    Double.valueOf(u[0]),
                    Vectors.dense(Double.valueOf(u[0]) + 2, Double.valueOf(u[0]) + 2, Double.valueOf(u[0]) + 2));
            return s;
        });//转化数据格式
        NaiveBayesModel model = NaiveBayes.train(data.rdd(), 1.0);//训练贝叶斯模型
        //打印label值
        for (double a : model.labels()) {
            System.out.println(a);
        }
        //打印先验概率
        for (double a : model.pi()) {
            System.out.println(a);
        }

//        double result = model.predict(Vectors.dense(0,0,10));//test
    }
}
