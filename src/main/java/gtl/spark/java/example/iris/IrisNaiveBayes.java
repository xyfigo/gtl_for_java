package gtl.spark.java.example.iris;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
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

public class IrisNaiveBayes {
    public static String DATA_FILE = "d:\\devs\\data\\spark\\iris.csv";

    public static void irisNaiveBayes(
            SparkSession spark,
            Dataset<Row> newDF
    ) {
        System.out.println(spark);
        JavaRDD<LabeledPoint> data = newDF.toJavaRDD()
                .map(r -> new LabeledPoint(
                        r.getInt(0),
                        Vectors.dense(
                                r.getDouble(1),
                                r.getDouble(2),
                                r.getDouble(3),
                                r.getDouble(4))));
        NaiveBayesModel nbm = NaiveBayes.train(data.rdd());
        System.out.println("[7.2,2.8,6.2,1.9]属于：" +
                nbm.predict(Vectors.dense(7.2, 2.8, 6.2, 1.9)));
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisNaiveBayes")
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
        irisNaiveBayes(spark, newDF);
    }
}
