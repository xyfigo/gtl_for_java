//package gtl.sqlite.java.example;
//
//import gtl.io.File;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.mllib.linalg.Vectors;
//import org.apache.spark.mllib.regression.LabeledPoint;
//import org.apache.spark.mllib.tree.RandomForest;
//import org.apache.spark.mllib.tree.model.RandomForestModel;
//import org.apache.spark.mllib.util.MLUtils;
//import scala.Tuple2;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//public class RandomForestClassification {
//    public static void main(String[] args) throws FileNotFoundException {
//        SparkConf sparkConf = new SparkConf().setAppName("JavaRandomForestClassification").setMaster("local[*]");
//        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
//        // Load and parse the data file.
//        //String datapath = "D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt";
//        String datapath = "D:\\devs\\studies\\scala\\HelloWorld\\TrainData.txt";
//        String testDatapath = "D:\\devs\\studies\\scala\\HelloWorld\\TestData.txt";
//        JavaRDD<LabeledPoint> trainingData = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
//        JavaRDD<LabeledPoint> testData = MLUtils.loadLibSVMFile(jsc.sc(), testDatapath).toJavaRDD();
//
////        String datapath = "D:\\devs\\studies\\scala\\HelloWorld\\Temp";
////        String testDatapath = "D:\\devs\\studies\\scala\\HelloWorld\\TempTest";
////        JavaRDD<String> data = jsc.textFile(datapath);//获取数据集
////        JavaRDD<String> Traindata = jsc.textFile(testDatapath);//获取数据集
////
////        JavaRDD<LabeledPoint> trainingData = data.map(r -> {
////            String t[] = r.split(" ");//“|”是转义字符,必须得加"\\"
////            LabeledPoint s = new LabeledPoint(
////                    Double.valueOf(t[0]),
////                    Vectors.dense(Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])));
////            return s;
////        }).cache();//转化数据格式
////
////        JavaRDD<LabeledPoint> testData = Traindata.map(r -> {
////            String t[] = r.split(" ");//“|”是转义字符,必须得加"\\"
////            LabeledPoint s = new LabeledPoint(
////                    Double.valueOf(t[0]),
////                    Vectors.dense(Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])));
////            return s;
////        }).cache();//转化数据格式
//
//
//        // Split the data into training and test sets (30% held out for testing)
////        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7, 0.3});
////        JavaRDD<LabeledPoint> trainingData = splits[0];
////        JavaRDD<LabeledPoint> testData = splits[1];
//
//        // Train a RandomForest model.
//        // Empty categoricalFeaturesInfo indicates all features are continuous.
//        Integer numClasses = 30;
//        Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
//        Integer numTrees = 3; // Use more in practice.
//        String featureSubsetStrategy = "auto"; // Let the algorithm choose.
//        String impurity = "gini";
//        Integer maxDepth = 5;
//        Integer maxBins = 32;
//        Integer seed = 12345;
//
//        RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses,
//                categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
//                seed);
//
//        // Evaluate model on test instances and compute test error
//        JavaPairRDD<Double, Double> predictionAndLabel =
//                testData.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
//       // predictionAndLabel.saveAsTextFile("D:\\devs\\studies\\scala\\HelloWorld\\result2.txt");
//
//       JavaRDD<Double> predictionResult = testData.map(p ->model.predict((p.features())));
//
//        //写下来
//        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Trainresult.txt"), true));
//        writer.println("90 158 165");
//        writer.println("1");
//        writer.println("DD");
//        for (Iterator iter = predictionResult.collect().iterator(); iter.hasNext();) {
//            writer.append(iter.next().toString());
//            writer.println();
//        }
//        writer.close();
//
//
//        double testErr =
//                predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testData.count();
//        System.out.println("Test Error: " + testErr);
//        System.out.println("Learned classification forest model:\n" + model.toDebugString());
//
//        // Save and load model
////    model.save(jsc.sc(), "target/tmp/myRandomForestClassificationModel");
////    RandomForestModel sameModel = RandomForestModel.load(jsc.sc(),
////      "target/tmp/myRandomForestClassificationModel");
//
//        jsc.stop();
//    }
//}
