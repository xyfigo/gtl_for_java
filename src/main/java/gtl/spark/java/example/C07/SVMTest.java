package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class SVMTest {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\wa2.txt";

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("SVMTest")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(), DATA_FILE).toJavaRDD();//读取数据集
        JavaRDD<LabeledPoint> splits[] = data.randomSplit(new double[]{0.7, 0.3}, 11L);//对数据集切分
        JavaRDD<LabeledPoint> parsedData = splits[0];//分割训练数据
        JavaRDD<LabeledPoint> parseTtest = splits[1];//分割测试数据
        SVMModel model = SVMWithSGD.train(parsedData.rdd(), 50);
        JavaPairRDD<Object, Object> predictionAndLabels = parseTtest.mapToPair(r -> {
            return new Tuple2<Object, Object>(model.predict(r.features()), r.label());
        }); //存储测试和预测值
        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());//创建验证类
        double accuracy = metrics.accuracy();//计算验证值
        System.out.println("accuracy = " + accuracy);//打印验证值  //注意和前面的逻辑回归的结果还是有区别的，毕竟是不同的算法

        double patient = model.predict(Vectors.dense(70, 3, 180.0, 4, 3));//计算患者可能性
        if (patient == 1)
            System.out.println("患者的胃癌有几率转移。"); //做出判断
        else
            System.out.println("患者的胃癌没有几率转移。");//做出判断
    }
}
