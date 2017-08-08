package gtl.spark.java.example.iris;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.clustering.GaussianMixture;
import org.apache.spark.mllib.clustering.GaussianMixtureModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.distribution.MultivariateGaussian;
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

public class IrisGaussianMixture {
    public static String DATA_FILE = "d:\\devs\\data\\spark\\iris.csv";

    public static void irisGaussianMixture(
            SparkSession spark, Dataset<Row> newDF) {
        newDF.createOrReplaceTempView("Iris");
        Dataset<Row> setosa = spark.sql(
                "SELECT SepalLength,SepalWidth,PetalLength,PetalWidth FROM Iris");
        JavaRDD<Vector> data = setosa
                .toJavaRDD()
                .map(r -> Vectors.dense(
                        r.getDouble(0),
                        r.getDouble(1),
                        r.getDouble(2),
                        r.getDouble(3)));
        GaussianMixture gm = new GaussianMixture();
        gm.setK(3);
        gm.setMaxIterations(50);
        GaussianMixtureModel gmm = gm.run(data);
        MultivariateGaussian[] mg = gmm.gaussians();
        int i = 0;
        double[] w = gmm.weights();
        for (MultivariateGaussian m : mg) {
            System.out.println(w[i++]);
            System.out.println(m.mu());
        }
        System.out.println(
                gmm.predict(Vectors.dense(
                        5.4, 3.1, 2.8, 0.8)));
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisGaussianMixture")
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
        irisGaussianMixture(spark, newDF);
    }
}
