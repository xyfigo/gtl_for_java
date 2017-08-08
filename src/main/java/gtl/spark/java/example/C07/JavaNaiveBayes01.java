package gtl.spark.java.example.C07;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;


/**
 * spark.mllib supports multinomial naive Bayes and Bernoulli naive Bayes.
 * These models are typically used for document classification.
 * Within that context, each observation is a document and each feature represents a term whose value is the frequency of the term (in multinomial naive Bayes) or a zero or one indicating whether the term was found in the document (in Bernoulli naive Bayes).
 * Feature values must be nonnegative.(特征值不能有负数)
 * The model type is selected with an optional parameter “multinomial” or “bernoulli” with “multinomial” as the default.
 * Additive smoothing can be used by setting the parameter $\lambda$ (default to $1.0$).
 * For document classification, the input feature vectors are usually sparse, and sparse vectors should be supplied as input to take advantage of sparsity.
 * Since the training data is only used once, it is not necessary to cache it.
 */


public class JavaNaiveBayes01 {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("JavaNaiveBayes").setMaster("local");
        JavaSparkContext js = new JavaSparkContext(sparkConf);
        //String path = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";
        String path = "D:\\devs\\studies\\scala\\HelloWorld\\Data.txt";
        JavaRDD<LabeledPoint> inputData = MLUtils.loadLibSVMFile(js.sc(), path).toJavaRDD();
        JavaRDD<LabeledPoint>[] tmp = inputData.randomSplit(new double[]{0.7, 0.3});
        JavaRDD<LabeledPoint> training = tmp[0]; // training set
        JavaRDD<LabeledPoint> test = tmp[1]; // test set
        NaiveBayesModel model = NaiveBayes.train(training.rdd(), 1.0);
        JavaPairRDD<Double, Double> predictionAndLabel =
                test.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
        double accuracy =
                predictionAndLabel.filter(pl -> pl._1().equals(pl._2())).count() / (double) test.count();
        System.out.println(accuracy);
        js.stop();
    }
}
