package gtl.spark.java.example.apache.ml;

import org.apache.spark.ml.feature.Imputer;
import org.apache.spark.ml.feature.ImputerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.List;

import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.apache.spark.sql.types.DataTypes.createStructField;

// $example off$

/**
 * https://www.cnblogs.com/charlotte77/p/5622325.html
 * 数据插值
 * <p>
 * An example demonstrating Imputer.
 * Run with:
 * bin/run-example ml.JavaImputerExample
 */

public class JavaImputer {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaImputer")
                .master("local[*]")
                .getOrCreate();

        List<Row> data = Arrays.asList(
                RowFactory.create(1.0, Double.NaN),
                RowFactory.create(2.0, Double.NaN),   //http://blog.csdn.net/tounaobun/article/details/8634620
                RowFactory.create(Double.NaN, 3.0),
                RowFactory.create(4.0, 4.0),
                RowFactory.create(5.0, 5.0)
        );
        StructType schema = new StructType(new StructField[]{
                createStructField("a", DoubleType, false),
                createStructField("b", DoubleType, false)
        });
        Dataset<Row> df = spark.createDataFrame(data, schema);

        Imputer imputer = new Imputer()
                .setInputCols(new String[]{"a", "b"})
                .setOutputCols(new String[]{"out_a", "out_b"});

        ImputerModel model = imputer.fit(df);
        model.transform(df).show();

        spark.stop();
    }
}
