package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.regression.AFTSurvivalRegression;
import org.apache.spark.ml.regression.AFTSurvivalRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.List;

/**
 * 生存回归
 * <p>
 * https://github.com/endymecy/spark-ml-source-analysis/blob/master/%E5%88%86%E7%B1%BB%E5%92%8C%E5%9B%9E%E5%BD%92/%E7%94%9F%E5%AD%98%E5%9B%9E%E5%BD%92/survival-regression.md
 * https://cosx.org/2016/06/five-useful-regression-models/
 *
 * VectorUDT()
 * User-defined type for Vector which allows easy interaction with SQL via Dataset.
 */
public class JavaAFTSurvivalRegression {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaAFTSurvivalRegressionExample")
                .master("local")
                .getOrCreate();

        List<Row> data = Arrays.asList(
                RowFactory.create(1.218, 1.0, Vectors.dense(1.560, -0.605)),
                RowFactory.create(2.949, 0.0, Vectors.dense(0.346, 2.158)),
                RowFactory.create(3.627, 0.0, Vectors.dense(1.380, 0.231)),
                RowFactory.create(0.273, 1.0, Vectors.dense(0.520, 1.151)),
                RowFactory.create(4.199, 0.0, Vectors.dense(0.795, -0.226))
        );
        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("censor", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });
        Dataset<Row> training = spark.createDataFrame(data, schema);
        double[] quantileProbabilities = new double[]{0.3, 0.6};

        AFTSurvivalRegression aft = new AFTSurvivalRegression()
                .setQuantileProbabilities(quantileProbabilities)
                .setQuantilesCol("quantiles");

        AFTSurvivalRegressionModel model = aft.fit(training);

        // Print the coefficients, intercept and scale parameter for AFT survival regression
        System.out.println("Coefficients: " + model.coefficients());
        System.out.println("Intercept: " + model.intercept());
        System.out.println("Scale: " + model.scale());

        model.transform(training).show(false);

        spark.stop();
    }
}

/**
 * Coefficients: [-0.4963111466650707,0.19844437699933098]
 * Intercept: 2.63809461510401
 * Scale: 1.5472345574364692
 * <p>
 * <p>
 * +-----+------+--------------+------------------+--------------------------------------+
 * |label|censor|features      |prediction        |quantiles                             |
 * +-----+------+--------------+------------------+--------------------------------------+
 * |1.218|1.0   |[1.56,-0.605] |5.718979487635007 |[1.1603238947151664,4.99545601027477] |
 * |2.949|0.0   |[0.346,2.158] |18.07652118149533 |[3.667545845471739,15.789611866277625]|
 * |3.627|0.0   |[1.38,0.231]  |7.381861804239096 |[1.4977061305190829,6.44796261233896] |
 * |0.273|1.0   |[0.52,1.151]  |13.577612501425284|[2.7547621481506854,11.8598722240697] |
 * |4.199|0.0   |[0.795,-0.226]|9.013097744073898 |[1.8286676321297826,7.87282650587843] |
 * +-----+------+--------------+------------------+--------------------------------------+
 */