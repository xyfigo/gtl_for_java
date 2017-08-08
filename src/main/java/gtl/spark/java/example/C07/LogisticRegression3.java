package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

//逻辑回归验证
public class LogisticRegression3 {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("LogisticRegression3")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(),DATA_FILE).toJavaRDD();//读取数据集
        JavaRDD<LabeledPoint> splits[] = data.randomSplit(new double[]{0.6, 0.4}, 11L);	//对数据集切分
        JavaRDD<LabeledPoint> parsedData = splits[0];//分割训练数据
        JavaRDD<LabeledPoint> parseTtest = splits[1];//分割测试数据
        LogisticRegressionModel model = LogisticRegressionWithSGD.train(parsedData.rdd(),50);//训练模型
        System.out.println(model.weights());//打印θ值
        JavaPairRDD<Object, Object> predictionAndLabels = parseTtest.mapToPair(r -> {
            return new Tuple2<Object, Object>(model.predict(r.features()), r.label());
        }); //存储测试和预测值
        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());//创建验证类
        double accuracy = metrics.accuracy();//计算验证值
        System.out.println("accuracy = " + accuracy);//打印验证值
    }
}
