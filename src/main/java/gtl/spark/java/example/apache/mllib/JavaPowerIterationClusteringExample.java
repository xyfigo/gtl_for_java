package gtl.spark.java.example.apache.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.PowerIterationClustering;
import org.apache.spark.mllib.clustering.PowerIterationClusteringModel;
import scala.Tuple3;

import java.util.Arrays;


/**
 * Java example for graph clustering using power iteration clustering (PIC).
 */

public class JavaPowerIterationClusteringExample {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("JavaPowerIterationClusteringExample").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        JavaRDD<Tuple3<Long, Long, Double>> similarities = sc.parallelize(Arrays.asList(
                new Tuple3<>(0L, 1L, 0.9),
                new Tuple3<>(1L, 2L, 0.9),
                new Tuple3<>(2L, 3L, 0.9),
                new Tuple3<>(3L, 4L, 0.1),
                new Tuple3<>(4L, 5L, 0.9)));

        PowerIterationClustering pic = new PowerIterationClustering()
                .setK(2)
                .setMaxIterations(10);
        PowerIterationClusteringModel model = pic.run(similarities);

        for (PowerIterationClustering.Assignment a : model.assignments().toJavaRDD().collect()) {
            System.out.println(a.id() + " -> " + a.cluster());
        }

        sc.stop();
    }
}
