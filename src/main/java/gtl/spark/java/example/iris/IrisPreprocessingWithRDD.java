package gtl.spark.java.example.iris;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.SparkSession;
import scala.Tuple5;

import java.util.regex.Pattern;

public class IrisPreprocessingWithRDD {
    private static final Pattern COMMA_SPLITTER = Pattern.compile(",");
    private static final Pattern NUMERIC = Pattern.compile("-?[0-9]+\\.?[0-9]*");
    public static String DATA_FILE = "d:\\devs\\data\\spark\\iris.txt";

    public static void calculateMeanAndVariance(
            JavaRDD<Tuple5<Integer, Double, Double, Double, Double>> tuples,
            Integer species, Integer attributeID) {
        JavaRDD<Double> setosaSepalLengths = tuples
                .filter(t -> t._1() == species)
                .map(t -> t._2());
        //setosaSepalLengths.foreach(s->System.out.println(s));
        JavaRDD<Vector> dv = setosaSepalLengths.map(d -> Vectors.dense(d));
        MultivariateStatisticalSummary s = Statistics.colStats(dv.rdd());
        System.out.println("setosa's sepal length mean:" + s.mean());
        System.out.println("setosa's sepal length variance:" + s.variance());
        System.out.println("setosa's sepal length normL1:" + s.normL1());
        System.out.println("setosa's sepal length normL2:" + s.normL2());
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisPreprocessingWithRDD")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> lines = sc.textFile(DATA_FILE);
        //lines.foreach(s->System.out.println(s));


        //对每行中的字符串以空格分割，形成新集合
        //     species    sepalLength  sepalWidth petalLength petalWidth
        JavaRDD<Tuple5<Integer, Double, Double, Double, Double>> tuples = lines.map(s -> {
            String[] ss = COMMA_SPLITTER.split(s);
            Integer species = 0;
            if (ss[4].compareTo("Iris-virginica") == 0) //1
                species = 3;
            else if (ss[4].compareTo("Iris-versicolor") == 0)//2
                species = 2;
            else if (ss[4].compareTo("Iris-setosa") == 0)//3
                species = 1;
            else
                species = 0;
            return new Tuple5<Integer, Double, Double, Double, Double>(
                    species, Double.valueOf(ss[0]), Double.valueOf(ss[1]), Double.valueOf(ss[2]), Double.valueOf(ss[3]));
        });
        tuples.foreach(s -> System.out.println(s));

        //计算Setosa的sepalLength的均值和方差
        JavaRDD<Double> setosaSepalLengths = tuples
                .filter(t -> t._1() == 1)
                .map(t -> t._2());
        //setosaSepalLengths.foreach(s->System.out.println(s));
        JavaRDD<Vector> dv = setosaSepalLengths.map(d -> Vectors.dense(d));
        MultivariateStatisticalSummary s = Statistics.colStats(dv.rdd());
        System.out.println("setosa's sepal length mean:" + s.mean());
        System.out.println("setosa's sepal length variance:" + s.variance());
        System.out.println("setosa's sepal length normL1:" + s.normL1());
        System.out.println("setosa's sepal length normL2:" + s.normL2());
        //计算Versicolor的sepalLength的均值和方差
        dv = tuples.filter(t -> t._1() == 2).map(t -> t._2()).map(d -> Vectors.dense(d));
        s = Statistics.colStats(dv.rdd());
        System.out.println("versicolor's sepal length mean:" + s.mean());
        System.out.println("versicolor's sepal length variance:" + s.variance());
        System.out.println("versicolor's sepal length normL1:" + s.normL1());
        System.out.println("versicolor's sepal length normL2:" + s.normL2());

        //计算virginica的sepalLength的均值和方差
        dv = tuples.filter(t -> t._1() == 3).map(t -> t._2()).map(d -> Vectors.dense(d));
        s = Statistics.colStats(dv.rdd());
        System.out.println("virginica's sepal length mean:" + s.mean());
        System.out.println("virginica's sepal length variance:" + s.variance());
        System.out.println("virginica's sepal length normL1:" + s.normL1());
        System.out.println("virginica's sepal length normL2:" + s.normL2());
        //计算setosa, versicolor, virginica的sepalLength的均值和方差
        dv = tuples.filter(t -> t._1() >= 1 && t._1() <= 3).map(t -> t._2()).map(d -> Vectors.dense(d));
        s = Statistics.colStats(dv.rdd());
        System.out.println("all sepal length mean:" + s.mean());
        System.out.println("all sepal length variance:" + s.variance());
        System.out.println("all sepal length normL1:" + s.normL1());
        System.out.println("all sepal length normL2:" + s.normL2());
    }
}
