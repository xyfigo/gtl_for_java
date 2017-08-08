package gtl.spark.java.example.C07;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;

//一元逻辑回归示例
public class LogisticRegression {
    public static String DATA_FILE = "D:\\devs\\data\\spark\\D07\\u.txt";

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("LogisticRegression")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> data = sc.textFile(DATA_FILE);                                                                //获取数据集
        JavaRDD<LabeledPoint> parsedData = data.map(r -> {
            String t[] = r.split("\\|");                                                                   //“|”是转义字符,必须得加"\\"
            LabeledPoint s = new LabeledPoint(
                    Double.valueOf(t[0]),
                    Vectors.dense(Double.valueOf(t[1])));
            return s;
        }).cache();                                                                                                 //转化数据格式
        LogisticRegressionModel model = LogisticRegressionWithSGD.train(parsedData.rdd(), 50);        //通过随机梯度下降算法迭代形成的逻辑回归模型
        Vector target = Vectors.dense(-1);                                                                //创建测试值
        double resulet = model.predict(target);                                                                    //根据模型计算结果
        System.out.println(resulet);                                                                                //打印结果
    }
}
