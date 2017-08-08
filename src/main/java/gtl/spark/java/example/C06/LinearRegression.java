package gtl.spark.java.example.C06;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;

public class LinearRegression {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("LinearRegressionWithSGD").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        String path = "D:\\devs\\data\\spark\\D06\\lpsa.data";
        JavaRDD<String> data = sc.textFile(path);
        JavaRDD<LabeledPoint> parsedData = data.map(line -> {
            String[] parts = line.split(",");
            String[] features = parts[1].split(" ");
            double[] v = new double[features.length];
            for (int i = 0; i < features.length - 1; i++) {
                v[i] = Double.parseDouble(features[i]);
            }
            return new LabeledPoint(Double.parseDouble(parts[0]), Vectors.dense(v));
        }).cache();
        LinearRegressionModel model =
                LinearRegressionWithSGD.train(JavaRDD.toRDD(parsedData), 100, 0.00000001);
        double result = model.predict(Vectors.dense(2, 2));
        System.out.println(result);  //4.92622224497903E-7
    }
}
