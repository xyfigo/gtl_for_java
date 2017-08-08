package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class JavaLogisticRegressionWithElasticNet {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaLogisticRegressionWithElasticNet")
                .master("local")
                .getOrCreate();

        // Load training data
        Dataset<Row> training = spark.read().format("libsvm")
                .load("D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt");

        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(training);

        // Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        // We can also use the multinomial family for binary classification
        LogisticRegression mlr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8)
                .setFamily("multinomial");

        // Fit the model
        LogisticRegressionModel mlrModel = mlr.fit(training);

        // Print the coefficients and intercepts for logistic regression with multinomial family
        System.out.println("Multinomial coefficients: " + lrModel.coefficientMatrix()
                + "\nMultinomial intercepts: " + mlrModel.interceptVector());

        spark.stop();
    }
}
