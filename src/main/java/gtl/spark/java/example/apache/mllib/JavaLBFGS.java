package gtl.spark.java.example.apache.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.optimization.LBFGS;
import org.apache.spark.mllib.optimization.LogisticGradient;
import org.apache.spark.mllib.optimization.SquaredL2Updater;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

import java.util.Arrays;


/**
 * http://www.cnblogs.com/hseagle/p/3927887.html
 * https://endymecy.gitbooks.io/spark-ml-source-analysis/content/%E6%9C%80%E4%BC%98%E5%8C%96%E7%AE%97%E6%B3%95/L-BFGS/lbfgs.html
 * http://www.aboutyun.com/thread-11019-1-1.html
 */


public class JavaLBFGS {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("L-BFGS Example").setMaster("local");
        SparkContext sc = new SparkContext(conf);

        String path = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();
        int numFeatures = data.take(1).get(0).features().size();

        // Split initial RDD into two... [60% training data, 40% testing data].
        JavaRDD<LabeledPoint> trainingInit = data.sample(false, 0.6, 11L);
        JavaRDD<LabeledPoint> test = data.subtract(trainingInit);

        // Append 1 into the training data as intercept.
        JavaPairRDD<Object, Vector> training = data.mapToPair(p ->
                new Tuple2<>(p.label(), MLUtils.appendBias(p.features())));
        training.cache();

        // Run training algorithm to build the model.
        int numCorrections = 10;
        double convergenceTol = 1e-4;
        int maxNumIterations = 20;
        double regParam = 0.1;
        Vector initialWeightsWithIntercept = Vectors.dense(new double[numFeatures + 1]);

        Tuple2<Vector, double[]> result = LBFGS.runLBFGS(
                training.rdd(),
                new LogisticGradient(),
                new SquaredL2Updater(),
                numCorrections,
                convergenceTol,
                maxNumIterations,
                regParam,
                initialWeightsWithIntercept);
        Vector weightsWithIntercept = result._1();
        double[] loss = result._2();

        LogisticRegressionModel model = new LogisticRegressionModel(
                Vectors.dense(Arrays.copyOf(weightsWithIntercept.toArray(), weightsWithIntercept.size() - 1)),
                (weightsWithIntercept.toArray())[weightsWithIntercept.size() - 1]);

        // Clear the default threshold.
        model.clearThreshold();

        // Compute raw scores on the test set.
        JavaPairRDD<Object, Object> scoreAndLabels = test.mapToPair(p ->
                new Tuple2<>(model.predict(p.features()), p.label()));

        // Get evaluation metrics.
        BinaryClassificationMetrics metrics =
                new BinaryClassificationMetrics(scoreAndLabels.rdd());
        double auROC = metrics.areaUnderROC();

        System.out.println("Loss of each step in training process");
        for (double l : loss) {
            System.out.println(l);
        }
        System.out.println("Area under ROC = " + auROC);

        sc.stop();
    }
}


/**
 * Loss of each step in training process
 * 0.6931471805599458
 * 0.33430243421184924
 * 0.19473095533260126
 * 0.002092887330265424
 * 7.1007812519668E-4
 * 7.1007812519668E-4
 * 5.751637018492139E-4
 * 9.267685208079923E-5
 * 5.772617267354332E-5
 * 5.5503714882828275E-5
 * 4.411477667803092E-5
 * 3.4942151536062546E-5
 * 3.142697044088829E-5
 * 2.6665104501440182E-5
 * 2.4223215971564615E-5
 * 2.3573285798424324E-5
 * 2.327347665720459E-5
 * 2.2982783580085882E-5
 * 2.2830177765470936E-5
 * 2.265404121978135E-5
 * 2.2515791851531316E-5
 * 2.2500163487865313E-5
 * Area under ROC = 1.0
 */
