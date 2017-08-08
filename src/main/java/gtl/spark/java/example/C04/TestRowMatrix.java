package gtl.spark.java.example.C04;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://programtalk.com/java-api-usage-examples/org.apache.spark.mllib.linalg.distributed.RowMatrix/

public class TestRowMatrix {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("Aggregate")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        Vector dv = Vectors.dense(1.0, 0.0, 3.0);
        Vector sv = Vectors.sparse(3, new int[]{0, 2}, new double[]{1.0, 3.0});
        List<Vector> data = new ArrayList<Vector>(Arrays.asList(dv, sv));  //  https://beginnersbook.com/2013/12/how-to-initialize-an-arraylist/

        List<Vector> data2 = Arrays.asList(
                Vectors.sparse(5, new int[]{1, 3}, new double[]{1.0, 7.0}),
                Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
                Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
        );

        JavaRDD<Vector> rows = sc.parallelize(data2);     // a JavaRDD of local vectors
        // Create a RowMatrix from an JavaRDD<Vector>.
        RowMatrix mat = new RowMatrix(rows.rdd());


        // Get its size.
        long m = mat.numRows();
        long n = mat.numCols();
        System.out.println(m);
        System.out.println(n);
    }
}
