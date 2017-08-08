package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.clustering.BisectingKMeans;
import org.apache.spark.ml.clustering.BisectingKMeansModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 二分k均值（bisecting k-means）算法
 */
public class JavaBisectingKMeans {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("JavaBisectingKMeansExample")
                .getOrCreate();

        // Loads data.
        Dataset<Row> dataset = spark.read().format("libsvm").load("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\data\\mllib\\sample_kmeans_data.txt");

        // Trains a bisecting k-means model.
        BisectingKMeans bkm = new BisectingKMeans().setK(2).setSeed(1);
        BisectingKMeansModel model = bkm.fit(dataset);

        // Evaluate clustering.
        double cost = model.computeCost(dataset);
        System.out.println("Within Set Sum of Squared Errors = " + cost);

        // Shows the result.
        System.out.println("Cluster Centers: ");
        Vector[] centers = model.clusterCenters();
        for (Vector center : centers) {
            System.out.println(center);
        }

        spark.stop();
    }
}


/**
 * Within Set Sum of Squared Errors = 0.11999999999994547
 * Cluster Centers:
 * [0.1,0.1,0.1]
 * [9.1,9.1,9.1]
 */