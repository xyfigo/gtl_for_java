package gtl.spark.java.example.apache.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

public class JavaLogisticRegressionWithLBFGS {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaLogisticRegressionWithLBFGS").setMaster("local");
        SparkContext sc = new SparkContext(conf);

        //String path = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";
        String path = "D:\\devs\\studies\\scala\\HelloWorld\\Data.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();

        // Split initial RDD into two... [60% training data, 40% testing data].
        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.6, 0.4}, 1234L);
        JavaRDD<LabeledPoint> training = splits[0].cache();
        JavaRDD<LabeledPoint> test = splits[1];

        // Run training algorithm to build the model.
        LogisticRegressionModel model = new LogisticRegressionWithLBFGS()
                .setNumClasses(10)
                .run(training.rdd());

        // Compute raw scores on the test set.
        JavaPairRDD<Object, Object> predictionAndLabels = test.mapToPair(p ->
                new Tuple2<>(model.predict(p.features()), p.label()));

        // Get evaluation metrics.
        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
        double accuracy = metrics.accuracy();
        System.out.println("Accuracy = " + accuracy);

        // Save and load model
//        model.save(sc, "target/tmp/javaLogisticRegressionWithLBFGSModel");
//        LogisticRegressionModel sameModel = LogisticRegressionModel.load(sc,
//                "target/tmp/javaLogisticRegressionWithLBFGSModel");

        sc.stop();
    }
}
