package gtl.spark.java.example.C07;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

/**
 * SVM算法
 * http://blog.csdn.net/sunbow0/article/details/45582771
 */


public class JavaSVMWithSGD {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaSVMWithSGD").setMaster("local");
        SparkContext sc = new SparkContext(conf);
        String path = "D:\\devs\\3rdparties\\spark\\spark-2.2.1\\data\\mllib\\sample_libsvm_data.txt";
        //String path = "D:\\devs\\studies\\scala\\HelloWorld\\Data.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();  //Loads binary labeled data in the LIBSVM format into an RDD[LabeledPoint], with number of features determined automatically and the default number of partitions.

        // Split initial RDD into two... [60% training data, 40% testing data].
        JavaRDD<LabeledPoint> training = data.sample(false, 0.7, 11L);
        training.cache();
        JavaRDD<LabeledPoint> test = data.subtract(training);  //去除training数据集

        // Run training algorithm to build the model.
        int numIterations = 100;
        SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);

        /**
         *
         * http://blog.csdn.net/seu_yang/article/details/52118683
         *
         * 在进行分类时，通常不仅希望知道该样本是被预测为0，1，还希望获得该样本被预测为0，1的概率
         val model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(trainingData)
         model.clearThreshold()
         默认Threshold为0.5，只需通过model.clearThreshold()函数去掉阈值即可获得分类概率
         * */

        // Clear the default threshold.
        model.clearThreshold();

        // Compute raw scores on the test set.
        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->
                new Tuple2<>(model.predict(p.features()), p.label()));  //features 标记点所代表的数据内容  label 显示标记数


        // 具体评价指标   http://cwiki.apachecn.org/pages/viewpage.action?pageId=9406579
        BinaryClassificationMetrics metrics =
                new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
        double auROC = metrics.areaUnderROC();

        System.out.println("Area under ROC = " + auROC);   //Area under ROC = 1.0

        // Save and load model
//        model.save(sc, "D:\\temp\\model\\javaSVMWithSGDModel");
//        SVMModel sameModel = SVMModel.load(sc, "D:\\temp\\model\\javaSVMWithSGDModel");

        sc.stop();
    }
}
