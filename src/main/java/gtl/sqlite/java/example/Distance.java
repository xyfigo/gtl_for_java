//package gtl.sqlite.java.example;
//
//import gtl.io.File;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.mllib.linalg.Vectors;
//import org.apache.spark.mllib.regression.LabeledPoint;
//import org.apache.spark.mllib.tree.RandomForest;
//import org.apache.spark.mllib.tree.model.RandomForestModel;
//import org.apache.spark.sql.SparkSession;
//import scala.Tuple2;
//
//import java.io.*;
//import java.util.*;
//import java.util.regex.Pattern;
//
//public class Distance {
//
//    private static final Pattern SPACE = Pattern.compile(" ");
//    private static final String datapath = "D:\\devs\\studies\\scala\\HelloWorld\\TrainDataRR.txt";
//    private static final String Traindatapath = "D:\\devs\\studies\\scala\\HelloWorld\\TestData.txt";
//
//    /**
//     * 计算点集1中每一个点与点集2中每个点的距离  不能同时操作两个是RDD
//     *
//     * @param points1
//     * @param points2
//     * @return 返回距离矩阵
//     */
//    public static JavaRDD<LabeledPoint> calculateDistances(JavaRDD<LabeledPoint> points1, List<double[]> points2) {
//        int pcs = points1.collect().size();
//        int ps = points2.size();
//        double[] T = new double[pcs];
//        JavaRDD<LabeledPoint> drdd = points1.map((LabeledPoint t) -> {
//            double[] v1 = t.features().toArray();
//            double d = 0.0;
//            for (int i = 0; i < ps; i++) {
//                double[] v2 = points2.get(i);
//                for (int k = 0; k < 3; k++) {
//                    d += Math.abs((v1[k] - v2[k]) * (v1[k] - v2[k]));
//                }
//                d = Math.sqrt(d);
//                //System.out.println("d大小是否正确"+d);
//                //TimeUnit.SECONDS.sleep(1);
//                T[i] = d;
//            }
//            LabeledPoint s = new LabeledPoint(
//                    Double.valueOf(t.label()),
//                    Vectors.dense(T));
//            return s;
//        });
//
//        return drdd;
//    }
//
//
//    /**
//     * 计算点集1中每一个点与点集2中每个点的距离  不能同时操作两个是RDD
//     * @param RandomData
//     * @param OriginData
//     * @return 返回距离矩阵
//     */
//    public static JavaRDD<DB_pointlabel> calculateDistances(JavaRDD<DB_point> RandomData, HashMap<DB_point, Integer> OriginData) {
//        int pcs = RandomData.collect().size();
//        System.out.println("number of pcs:"+pcs);
//        JavaRDD<DB_pointlabel> drdd = RandomData.map((DB_point t) -> {
//            double temp = Double.MAX_VALUE;
//            double result;
//            DB_pointlabel pl=new DB_pointlabel();
//            for (Map.Entry<DB_point, Integer> entry : OriginData.entrySet()) {
//                //if ((t.getBH_Z() >= entry.getKey().getBH_Z()) && (t.getBH_Z() <= entry.getKey().getBH_Z() + 1)) {
//                result = Math.sqrt(Math.pow(t.getBH_X() - entry.getKey().getBH_X(), 2) + Math.pow(t.getBH_Y() - entry.getKey().getBH_Y(), 2) + Math.pow(t.getBH_X() - entry.getKey().getBH_X(), 2));
//                if (temp > result) {
//                    temp = result;
//                    pl.setBH_X(entry.getKey().getBH_X());
//                    pl.setBH_Y(entry.getKey().getBH_Y());
//                    pl.setBH_Z(entry.getKey().getBH_Z());
//                    pl.setBH_L(entry.getValue());
//                }
//            }
//            //}
//            return new DB_pointlabel(pl.getBH_X(),pl.getBH_Y(),pl.getBH_Z(),pl.getBH_L());
//        });
//        return drdd;
//    }
//
//
//    public static void WriteDD(JavaRDD<LabeledPoint> result) throws FileNotFoundException {
//        //写成libsvm的格式
//        String AA = String.valueOf(result.collect().size() + ".txt");
//        PrintWriter writer3 = new PrintWriter(new FileOutputStream(new gtl.io.File(AA), true));
//        for (int i = 0; i < result.collect().size(); i++) {
//            writer3.append(Double.toString(result.collect().get(i).label()));
//            double[] temp = result.collect().get(i).features().toArray();
//            for (int j = 1; j <= temp.length; j++) {
//                if (temp[j - 1] != 0.0)
//                    writer3.append(" " + j + ":" + temp[j - 1]);
//            }
//            writer3.println();
//        }
//        writer3.close();
//    }
//
//    public static List<double[]> readTxtFileIntoStringArrList(String filePath) {
//        List<double[]> list = new ArrayList<>();
//        try {
//            String encoding = "UTF-8";
//            File file = new File(filePath);
//            if (file.isFile() && file.exists()) { // 判断文件是否存在
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file), encoding);// 考虑到编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//                    String[] s = new String(lineTxt).split(" ");
//                    list.add(new double[]{Double.valueOf(s[0]), Double.valueOf(s[1]), Double.valueOf(s[2])});
//                }
//                bufferedReader.close();
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//
//    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
//
//        SparkSession spark = SparkSession
//                .builder()
//                .master("local[*]")
//                .appName("SVM")
//                .getOrCreate();
//        List<double[]> PData = readTxtFileIntoStringArrList("D:\\devs\\studies\\scala\\HelloWorld\\TrainDataOrigin.txt");
//        //List<double[]> PTraindata = new LinkedList<>();
////        double[] A=new double[3];
//        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
//        JavaRDD<String> data = sc.textFile(datapath);//获取数据集
//        JavaRDD<String> Traindata = sc.textFile(Traindatapath);//获取数据集
//
//        JavaRDD<LabeledPoint> parsedData = data.map(r -> {
//            String t[] = r.split(" ");//“|”是转义字符,必须得加"\\"
////            A[0]=Double.valueOf(t[1]);
////            A[1]=Double.valueOf(t[2]);
////            A[2]=Double.valueOf(t[3]);
////            System.out.println("A[0]"+A[0]+"A[1]"+A[1]+"A[2]"+A[2]);
////            PData.add(A);
//            // System.out.println("PData.size()"+PData.size());
//            LabeledPoint s = new LabeledPoint(
//                    Double.valueOf(t[0]),
//                    Vectors.dense(Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])));
//            return s;
//        }).cache();//转化数据格式
//
//        JavaRDD<LabeledPoint> parsedTraindata = Traindata.map(r -> {
//            String t[] = r.split(" ");//“|”是转义字符,必须得加"\\"
//            //  PTraindata.add(new double[]{Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])});
//            LabeledPoint s = new LabeledPoint(
//                    Double.valueOf(t[0]),
//                    Vectors.dense(Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])));
//            return s;
//        }).cache();//转化数据格式
//
//        if (PData.size() != 0) {
//            System.out.println("PData.size()" + PData.size());
//            //   TimeUnit.SECONDS.sleep(30);
//            JavaRDD<LabeledPoint> trainingData = calculateDistances(parsedData, PData);
//            System.out.println("result.count()" + trainingData.count());
//           // trainingData.saveAsTextFile("D:\\devs\\studies\\scala\\HelloWorld\\Temp");
//            // WriteDD(result);
//            JavaRDD<LabeledPoint> testData = calculateDistances(parsedTraindata, PData);
//            System.out.println("Traindataresult.count()" + testData.count());
//           // testData.saveAsTextFile("D:\\devs\\studies\\scala\\HelloWorld\\TempTest");
//            //WriteDD(Traindataresult);
//            // Train a RandomForest model.
//            // Empty categoricalFeaturesInfo indicates all features are continuous.
//            Integer numClasses = 30;
//            Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
//            Integer numTrees = 3; // Use more in practice.
//            String featureSubsetStrategy = "auto"; // Let the algorithm choose.
//            String impurity = "gini";
//            Integer maxDepth = 5;
//            Integer maxBins = 32;
//            Integer seed = 12345;
//
//            RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses,
//                    categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
//                    seed);
//
//            // Evaluate model on test instances and compute test error
//            JavaPairRDD<Double, Double> predictionAndLabel =
//                    testData.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
//            // predictionAndLabel.saveAsTextFile("D:\\devs\\studies\\scala\\HelloWorld\\result2.txt");
//
//            JavaRDD<Double> predictionResult = testData.map(p -> model.predict((p.features())));
//
//            //写下来
//            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Trainresult.txt"), true));
//            writer.println("90 158 165");
//            writer.println("1");
//            writer.println("DD");
//            for (Iterator iter = predictionResult.collect().iterator(); iter.hasNext(); ) {
//                writer.append(iter.next().toString());
//                writer.append(iter.next().toString());
//                writer.println();
//            }
//            writer.close();
//
//
//            double testErr =
//                    predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testData.count();
//            System.out.println("Test Error: " + testErr);
//            System.out.println("Learned classification forest model:\n" + model.toDebugString());
//
//        }
//
//        sc.stop();
//        spark.stop();
//    }
//}
