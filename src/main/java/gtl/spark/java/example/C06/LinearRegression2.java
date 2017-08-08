package gtl.spark.java.example.C06;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;

public class LinearRegression2 {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("JavaLinearRegressionWithSGD").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        String path = "D:\\devs\\data\\spark\\D06\\lr.txt";
        JavaRDD<String> data = sc.textFile(path);
        JavaRDD<LabeledPoint> parsedData = data.map(line -> {
            String[] parts = line.split("\\|");
//            System.out.println(parts[0]);
            String[] features = parts[1].split(",");
            double[] v = new double[features.length];
            for (int i = 0; i < features.length - 1; i++) {
                v[i] = Double.parseDouble(features[i]);
            }
            return new LabeledPoint(Double.parseDouble(parts[0]), Vectors.dense(v));
        });
        parsedData.cache();
        LinearRegressionModel model =
                LinearRegressionWithSGD.train(JavaRDD.toRDD(parsedData), 1000, 0.00000001);
        double result = model.predict(Vectors.dense(8,30));  //6.14558341949365E-5
        System.out.println(result);

    }


}
