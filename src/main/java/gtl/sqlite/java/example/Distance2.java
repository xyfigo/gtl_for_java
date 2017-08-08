//package gtl.sqlite.java.example;
//
//import gtl.io.File;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.mllib.linalg.Vectors;
//import org.apache.spark.mllib.regression.LabeledPoint;
//import org.apache.spark.sql.SparkSession;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Pattern;
//
//public class Distance2 {
//
//    private static final Pattern SPACE = Pattern.compile(" ");
//    private static final String hdfsdatapath = "hdfs://192.168.0.130:9000/alidata/resultMap2.txt";
//    private static final String hdfsTraindatapath = "hdfs://192.168.0.130:9000/alidata/TestData.txt";
//    private static final String datapath = "D:\\devs\\studies\\scala\\HelloWorld\\resultMap2.txt";
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
//
//    /**
//     * 计算点集1中每一个点与点集2中每个点的距离  不能同时操作两个是RDD
//     *
//     * @param RandomData
//     * @param OriginData
//     * @return 返回距离矩阵
//     */
//    public static JavaRDD<String> calculateDistances2(JavaRDD<DB_point> RandomData, HashMap<DB_point, Integer> OriginData) {
//        JavaRDD<String> drdd = RandomData.map((DB_point t) -> {
//            double temp = Double.MAX_VALUE;
//            double result;
//            String SResult = "";
//            Iterator<Map.Entry<DB_point, Integer>> it = OriginData.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<DB_point, Integer> entry = it.next();
//                DB_point key = entry.getKey();
//                Integer value = entry.getValue();
////                System.out.println(entry.getKey());
////                System.out.println(entry.getValue());
//              //  if ((t.getBH_Z().equals(entry.getKey().getBH_Z()))) {
//                    result = Math.sqrt(Math.pow(t.getBH_X() - key.getBH_X(), 2) + Math.pow(t.getBH_Y() - key.getBH_Y(), 2) + Math.pow(t.getBH_Z() - key.getBH_Z(), 2));
//                    if (temp > result) {
//                        temp = result;
////                        System.out.println("temp:" + temp);
////                        System.out.println("entry.getValue().toString()"+value.toString());
//                        SResult = value.toString();
//                    }
//                }
//           // }
////            if (!SResult.equals("")){
////                System.out.println("SResult" + SResult);
////                TimeUnit.SECONDS.sleep(3);
////            }
//            return SResult;
//        });
//        return drdd;
//    }
//
//
//    /**
//     * 计算点集1中每一个点与周围三个钻孔每一段的距离，然后得到一个最小值，将其结果为该点属性值
//     *
//     * @param points1
//     * @param points2
//     * @return 返回距离矩阵
//     */
//    public static JavaRDD<LabeledPoint> calculateDistances3(JavaRDD<LabeledPoint> points1, List<double[]> points2) {
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
//    public static void WriteDD(JavaRDD<LabeledPoint> result) throws FileNotFoundException {
//        //写成libsvm的格式
//        String AA = String.valueOf(result.collect().size() + ".txt");
//        PrintWriter writer3 = new PrintWriter(new FileOutputStream(new File(AA), true));
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
//    public static HashMap<DB_point, Integer> readTxtFileIntoStringArrList(String filePath) {
//        HashMap<DB_point, Integer> result = new HashMap<DB_point, Integer>();
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
//                    result.put(new DB_point(Double.valueOf(s[1]), Double.valueOf(s[2]), Double.valueOf(s[3])), Integer.valueOf(s[0]));
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
//        return result;
//    }
//
//
//    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
//        SparkSession spark = SparkSession
//                .builder()
//                .master("local[*]")
//                .appName("SVM")
//                .getOrCreate();
//        HashMap<DB_point, Integer> PData = readTxtFileIntoStringArrList(datapath);
//        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
//        sc.addJar("hdfs://192.168.0.130:9000/alidata/HelloWorld.jar");
//        JavaRDD<String> Traindata = sc.textFile(Traindatapath);//获取数据集
//        JavaRDD<DB_point> parsedTraindata = Traindata.map(r -> {
//            String t[] = r.split(" ");//“|”是转义字符,必须得加"\\"
//            DB_point s = new DB_point(Double.valueOf(t[0]), Double.valueOf(t[1]), Double.valueOf(t[2]));
//            return s;
//        }).cache();//转化数据格式
//        System.out.println("parsedTraindata.count" + parsedTraindata.collect().size());
//
//
//        if (PData.size() != 0) {
//            System.out.println("PData.size()" + PData.size());
//            //   TimeUnit.SECONDS.sleep(30);
//            JavaRDD<String> trainingDataresult = calculateDistances2(parsedTraindata, PData);
//            System.out.println("trainingDataresult.count()" + trainingDataresult.collect().size());
//            //trainingDataresult.saveAsTextFile("D:\\devs\\studies\\scala\\HelloWorld\\Re");
//
//            //写下来
//            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Trainresult.txt"), true));
//            writer.println("90 158 434");
//            writer.println("1");
//            writer.println("DD");
//            for (Iterator iter = trainingDataresult.collect().iterator(); iter.hasNext(); ) {
//                writer.append(iter.next().toString());
//                writer.println();
//            }
//            writer.close();
//        }
//
//        sc.stop();
////        spark.stop();
//    }
//}
