package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

// 多元逻辑回归示例
public class LogisticRegression2 {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("LogisticRegression2")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(), DATA_FILE).toJavaRDD();//读取数据文件
        LogisticRegressionModel model = LogisticRegressionWithSGD.train(data.rdd(), 50);//训练数据模型
        System.out.println(model.weights());//打印θ值
        System.out.println(model.weights().size());//打印θ值个数
        System.out.println(Arrays.stream(model.weights().toArray()).filter(x -> x != 0).toArray().length);//打印θ值不为0的数
    }
}
