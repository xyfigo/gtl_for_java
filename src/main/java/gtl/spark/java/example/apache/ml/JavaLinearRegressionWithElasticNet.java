package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class JavaLinearRegressionWithElasticNet {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaLinearRegressionWithElasticNet")
                .master("local")
                .getOrCreate();

        // Load training data.
        Dataset<Row> training = spark.read().format("libsvm")
                .load("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\data\\mllib\\sample_linear_regression_data.txt");

        LinearRegression lr = new LinearRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model.
        LinearRegressionModel lrModel = lr.fit(training);

        // Print the coefficients and intercept for linear regression.
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        // Summarize the model over the training set and print out some metrics.
        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();
        System.out.println("numIterations: " + trainingSummary.totalIterations());
        System.out.println("objectiveHistory: " + Vectors.dense(trainingSummary.objectiveHistory()));
        trainingSummary.residuals().show();
        System.out.println("RMSE: " + trainingSummary.rootMeanSquaredError());
        System.out.println("r2: " + trainingSummary.r2());

        spark.stop();
    }
}

/**
 * +--------------------+
 * |           residuals|
 * +--------------------+
 * |  -9.889232683103197|
 * |  0.5533794340053554|
 * |  -5.204019455758823|
 * | -20.566686715507508|
 * |    -9.4497405180564|
 * |  -6.909112502719486|
 * |  -10.00431602969873|
 * |   2.062397807050484|
 * |  3.1117508432954772|
 * | -15.893608229419382|
 * |  -5.036284254673026|
 * |   6.483215876994333|
 * |  12.429497299109002|
 * |  -20.32003219007654|
 * | -2.0049838218725005|
 * | -17.867901734183793|
 * |   7.646455887420495|
 * | -2.2653482182417406|
 * |-0.10308920436195645|
 * |  -1.380034070385301|
 * +--------------------+
 * only showing top 20 rows
 * <p>
 * RMSE: 10.189077167598475
 * r2: 0.022861466913958184
 */
