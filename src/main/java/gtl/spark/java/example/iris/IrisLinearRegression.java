package gtl.spark.java.example.iris;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.IntegerType;

public class IrisLinearRegression {
    public static String DATA_FILE = "d:\\devs\\data\\spark\\iris.csv";

    public static void linearRegression(
            SparkSession spark,
            Dataset<Row> newDF) {
        //计算setosa的萼片长度与宽度的相关系数
        newDF.createOrReplaceTempView("Iris");
        Dataset<Row> setosa = spark.sql(
                "SELECT SepalLength,SepalWidth FROM Iris where Species=1");
        JavaRDD<LabeledPoint> data = setosa
                .toJavaRDD()
                .map(r -> new LabeledPoint(r.getDouble(0), Vectors.dense(r.getDouble(1))));
        LinearRegressionModel model = LinearRegressionWithSGD.train(data.rdd(), 10, 0.1);
        System.out.println("setosa萼片长度与宽度的线性回归方程：");
        System.out.println("y=" + model.weights() + " * x + " + model.intercept());
        //构建真实数据与预测数据的RDD
        JavaRDD<Tuple2<Double, Double>> realAndPredictValues =
                data.map(t -> new Tuple2<Double, Double>(t.label(), model.predict(t.features())));
        //计算均方误差
        JavaRDD<Vector> MSE = realAndPredictValues.map(t -> Vectors.dense(Math.pow(t._1() - t._2(), 2)));
        System.out.println("setosa萼片长度与宽度的线性回归的均方误差为："
                + Statistics.colStats(MSE.rdd()).mean());
    }

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisLinearRegression")
                .getOrCreate();
        Dataset<Row> df = spark.read().csv(DATA_FILE);
        JavaRDD<Row> lines = df.toJavaRDD().map(r -> {
            Integer species = 0;
            if (r.getString(4).compareTo("Iris-virginica") == 0) //1
                species = 3;
            else if (r.getString(4).compareTo("Iris-versicolor") == 0)//2
                species = 2;
            else if (r.getString(4).compareTo("Iris-setosa") == 0)//3
                species = 1;
            else
                species = 0;
            return RowFactory.create(species,
                    Double.valueOf(r.getString(0)),
                    Double.valueOf(r.getString(1)),
                    Double.valueOf(r.getString(2)),
                    Double.valueOf(r.getString(3)));
        });
        //建立数据框架的结构
        StructField species = DataTypes.createStructField(
                "Species", IntegerType, false);
        StructField sepalLength = DataTypes.createStructField(
                "SepalLength", DoubleType, false);
        StructField sepalWidth = DataTypes.createStructField(
                "SepalWidth", DoubleType, false);
        StructField petalLength = DataTypes.createStructField(
                "PetalLength", DoubleType, false);
        StructField petalWidth = DataTypes.createStructField(
                "PetalWidth", DoubleType, false);
        List<StructField> sfs = new ArrayList<>(5);
        sfs.add(species);
        sfs.add(sepalLength);
        sfs.add(sepalWidth);
        sfs.add(petalLength);
        sfs.add(petalWidth);
        StructType st = DataTypes.createStructType(sfs);
        //创建新的数据集
        Dataset<Row> newDF = spark.createDataFrame(lines, st);
        linearRegression(spark, newDF);
    }

}
