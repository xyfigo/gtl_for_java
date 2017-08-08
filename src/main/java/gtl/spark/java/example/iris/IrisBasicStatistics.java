package gtl.spark.java.example.iris;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.IntegerType;

public class IrisBasicStatistics {
    public static String DATA_FILE = "d:\\devs\\data\\spark\\iris.csv";

    public static void calculateMeanAndVariance(
            SparkSession spark,
            Dataset<Row> newDF) {
        //计算Setosa的萼片长度数据的均值与方差
        newDF.createOrReplaceTempView("Iris");
        Dataset<Row> setosaSepalLengths = spark.sql(
                "SELECT SepalLength FROM Iris where Species=1");
        setosaSepalLengths.show();
        setosaSepalLengths.printSchema();
        JavaRDD<Vector> dv = setosaSepalLengths
                .toJavaRDD()
                .map(d -> Vectors.dense(d.getDouble(0)));
        MultivariateStatisticalSummary s = Statistics.colStats(dv.rdd());
        System.out.println("setosa萼片长度数据个数:" + s.count());
        System.out.println("setosa萼片长度数据最大值:" + s.max());
        System.out.println("setosa萼片长度数据最小值:" + s.min());
        System.out.println("setosa萼片长度数据均值:" + s.mean());
        System.out.println("setosa萼片长度数据欧氏距离:" + s.normL1());
        System.out.println("setosa萼片长度数据曼哈顿距离:" + s.normL2());
        System.out.println("setosa萼片长度数据标准方差:" + s.variance());
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisBasicStatistics")
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
        calculateMeanAndVariance(spark, newDF);
    }
}
