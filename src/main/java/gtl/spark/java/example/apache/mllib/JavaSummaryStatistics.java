package gtl.spark.java.example.apache.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;

import java.util.Arrays;

public class JavaSummaryStatistics {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("JavaSummaryStatisticsExample").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // an RDD of Vectors
        JavaRDD<Vector> mat = jsc.parallelize(
                Arrays.asList(
                        Vectors.dense(1.0, 10.0, 100.0),
                        Vectors.dense(2.0, 20.0, 200.0),
                        Vectors.dense(3.0, 30.0, 300.0)
                )
        );

        // Compute column summary statistics.
        MultivariateStatisticalSummary summary = Statistics.colStats(mat.rdd());
        System.out.println(summary.mean());  // 每一列的均值
        System.out.println(summary.variance());  //每一列的方差
        System.out.println(summary.numNonzeros());  //每一列中数据不为0的个数

        jsc.stop();
    }
}

/**
 * [2.0,20.0,200.0]
 * [1.0,100.0,10000.0]
 * [3.0,3.0,3.0]
 */