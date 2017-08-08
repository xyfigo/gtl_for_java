package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class BayesTest {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\data.txt";

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("BayesTest")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> datai = sc.textFile(DATA_FILE);//读取数据集
        JavaRDD<LabeledPoint> data = datai.map(r -> {
            String[] u = r.split(",");
            String[] t = u[1].split(" ");   //因为数据格式出错  导致程序出错   因此在必要的时候需要检查数据
            LabeledPoint s = new LabeledPoint(
                    Double.valueOf(u[0]),
                    Vectors.dense(Double.valueOf(t[0]+2), Double.valueOf(t[1]+2), Double.valueOf(t[2]+2)));
            return s;
        });//转化数据格式
        JavaRDD<LabeledPoint> splits[] = data.randomSplit(new double[]{0.7, 0.3}, 11L);//对数据集切分
        JavaRDD<LabeledPoint> trainingData = splits[0];//分割训练数据
        JavaRDD<LabeledPoint> testData = splits[1];//分割测试数据
        NaiveBayesModel model = NaiveBayes.train(trainingData.rdd(), 1.0);//训练贝叶斯模型
        JavaPairRDD<Object, Object> predictionAndLabels = testData.mapToPair(r -> {
            return new Tuple2<Object, Object>(model.predict(r.features()), r.label());
        }); //存储测试和预测值
        double accuracy = 1.0 * predictionAndLabels.filter(r -> r._1().equals(r._2())).count() / testData.count();
        System.out.println("Accuracy = " + accuracy);//打印准确度
    }
}
