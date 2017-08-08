package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.classification.NaiveBayes;
import org.apache.spark.ml.classification.NaiveBayesModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * An example for Naive Bayes Classification.
 */
public class JavaNaiveBayes {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("JavaNaiveBayesExample")
                .getOrCreate();

        // Load training data
        Dataset<Row> dataFrame =
                spark.read().format("libsvm").load("D:\\devs\\data\\spark\\D07\\sample_libsvm_data.txt");
        // Split the data into train and test
        Dataset<Row>[] splits = dataFrame.randomSplit(new double[]{0.6, 0.4}, 1234L);
        Dataset<Row> train = splits[0];
        Dataset<Row> test = splits[1];

        // create the trainer and set its parameters
        NaiveBayes nb = new NaiveBayes();

        // train the model
        NaiveBayesModel model = nb.fit(train);

        // Select example rows to display.
        Dataset<Row> predictions = model.transform(test);
        predictions.show();

        // compute accuracy on the test set
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("label")
                .setPredictionCol("prediction")
                .setMetricName("accuracy");
        double accuracy = evaluator.evaluate(predictions);
        System.out.println("Test set accuracy = " + accuracy);


        spark.stop();
    }
}


/**
 * +-----+--------------------+--------------------+-----------+----------+
 * |label|            features|       rawPrediction|probability|prediction|
 * +-----+--------------------+--------------------+-----------+----------+
 * |  0.0|(692,[95,96,97,12...|[-174115.98587057...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[98,99,100,1...|[-178402.52307196...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[100,101,102...|[-100905.88974016...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[123,124,125...|[-244784.29791241...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[123,124,125...|[-196900.88506109...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[124,125,126...|[-238164.45338794...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[124,125,126...|[-184206.87833381...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[127,128,129...|[-214174.52863813...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[127,128,129...|[-182844.62193963...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[128,129,130...|[-246557.10990301...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[152,153,154...|[-208282.08496711...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[152,153,154...|[-243457.69885665...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[153,154,155...|[-260933.50931276...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[154,155,156...|[-220274.72552901...|  [1.0,0.0]|       0.0|
 * |  0.0|(692,[181,182,183...|[-154830.07125175...|  [1.0,0.0]|       0.0|
 * |  1.0|(692,[99,100,101,...|[-145978.24563975...|  [0.0,1.0]|       1.0|
 * |  1.0|(692,[100,101,102...|[-147916.32657832...|  [0.0,1.0]|       1.0|
 * |  1.0|(692,[123,124,125...|[-139663.27471685...|  [0.0,1.0]|       1.0|
 * |  1.0|(692,[124,125,126...|[-129013.44238751...|  [0.0,1.0]|       1.0|
 * |  1.0|(692,[125,126,127...|[-81829.799906049...|  [0.0,1.0]|       1.0|
 * +-----+--------------------+--------------------+-----------+----------+
 * only showing top 20 rows
 */