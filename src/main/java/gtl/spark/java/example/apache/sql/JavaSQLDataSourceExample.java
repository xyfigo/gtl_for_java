package gtl.spark.java.example.apache.sql;

import gtl.sqlite.java.example.DB_BOREHOLES_POSITION;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JavaSQLDataSourceExample {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data sources example")
                .config("spark.some.config.option", "some-value")
                .master("local[*]")
                .getOrCreate();

        //runBasicDataSourceExample(spark);
        //runBasicParquetExample(spark);
        //runParquetSchemaMergingExample(spark);
        //runJsonDatasetExample(spark);
        runJdbcDatasetExample(spark);

        spark.stop();
    }

    /**
     * parquet文件格式
     * Parquet是一种柱状(columnar)格式，可以被许多其它的数据处理系统支持。Spark SQL提供支持读和写Parquet文件的功能，这些文件可以自动地保留原始数据的模式。
     * <p>
     * http://blog.csdn.net/yu616568/article/details/50993491
     * https://endymecy.gitbooks.io/spark-programming-guide-zh-cn/content/spark-sql/data-sources/parquet-files.html
     */

    private static void runBasicDataSourceExample(SparkSession spark) {
        Dataset<Row> usersDF = spark.read().load("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\users.parquet");
        usersDF.select("name", "favorite_color").write().save("namesAndFavColors.parquet");
        Dataset<Row> peopleDF =
                spark.read().format("json").load("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\people.json");
        peopleDF.select("name", "age").write().format("parquet").save("namesAndAges.parquet");
        Dataset<Row> sqlDF =
                spark.sql("SELECT * FROM parquet.`D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\users.parquet`");
        peopleDF.write().bucketBy(42, "name").sortBy("age").saveAsTable("people_bucketed");


        usersDF
                .write()
                .partitionBy("favorite_color")
                .format("parquet")
                .save("namesPartByColor.parquet");


        peopleDF
                .write()
                .partitionBy("favorite_color")
                .bucketBy(42, "name")
                .saveAsTable("people_partitioned_bucketed");

        spark.sql("DROP TABLE IF EXISTS people_bucketed");
        spark.sql("DROP TABLE IF EXISTS people_partitioned_bucketed");
    }

    private static void runBasicParquetExample(SparkSession spark) {
        Dataset<Row> peopleDF = spark.read().json("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\people.json");

        // DataFrames can be saved as Parquet files, maintaining the schema information
        peopleDF.write().parquet("people.parquet");

        // Read in the Parquet file created above.
        // Parquet files are self-describing so the schema is preserved
        // The result of loading a parquet file is also a DataFrame
        Dataset<Row> parquetFileDF = spark.read().parquet("people.parquet");

        // Parquet files can also be used to create a temporary view and then used in SQL statements
        parquetFileDF.createOrReplaceTempView("parquetFile");
        Dataset<Row> namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19");
        Dataset<String> namesDS = namesDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                Encoders.STRING());
        namesDS.show();
        // +------------+
        // |       value|
        // +------------+
        // |Name: Justin|
        // +------------+
    }

    private static void runParquetSchemaMergingExample(SparkSession spark) {
        List<Square> squares = new ArrayList<>();
        for (int value = 1; value <= 5; value++) {
            Square square = new Square();
            square.setValue(value);
            square.setSquare(value * value);
            squares.add(square);
        }

        // Create a simple DataFrame, store into a partition directory
        Dataset<Row> squaresDF = spark.createDataFrame(squares, Square.class);
        squaresDF.write().parquet("data/test_table/key=1");

        List<Cube> cubes = new ArrayList<>();
        for (int value = 6; value <= 10; value++) {
            Cube cube = new Cube();
            cube.setValue(value);
            cube.setCube(value * value * value);
            cubes.add(cube);
        }

        // Create another DataFrame in a new partition directory,
        // adding a new column and dropping an existing column
        Dataset<Row> cubesDF = spark.createDataFrame(cubes, Cube.class);
        cubesDF.write().parquet("data/test_table/key=2");

        // Read the partitioned table
        Dataset<Row> mergedDF = spark.read().option("mergeSchema", true).parquet("data/test_table");
        mergedDF.printSchema();

        // The final schema consists of all 3 columns in the Parquet files together
        // with the partitioning column appeared in the partition directory paths
        // root
        //  |-- value: int (nullable = true)
        //  |-- square: int (nullable = true)
        //  |-- cube: int (nullable = true)
        //  |-- key: int (nullable = true)
    }

    private static void runJsonDatasetExample(SparkSession spark) {
        // A JSON dataset is pointed to by path.
        // The path can be either a single text file or a directory storing text files
        Dataset<Row> people = spark.read().json("D:\\devs\\3rdparties\\spark\\spark-2.2.1\\examples\\src\\main\\resources\\people.json");
        // The inferred schema can be visualized using the printSchema() method
        people.printSchema();
        // root
        //  |-- age: long (nullable = true)
        //  |-- name: string (nullable = true)

        // Creates a temporary view using the DataFrame
        people.createOrReplaceTempView("people");

        // SQL statements can be run by using the sql methods provided by spark
        Dataset<Row> namesDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
        namesDF.show();
        // +------+
        // |  name|
        // +------+
        // |Justin|
        // +------+

        // Alternatively, a DataFrame can be created for a JSON dataset represented by
        // a Dataset<String> storing one JSON object per string.
        List<String> jsonData = Arrays.asList(
                "{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
        Dataset<String> anotherPeopleDataset = spark.createDataset(jsonData, Encoders.STRING());
        Dataset<Row> anotherPeople = spark.read().json(anotherPeopleDataset);
        anotherPeople.show();
        // +---------------+----+
        // |        address|name|
        // +---------------+----+
        // |[Columbus,Ohio]| Yin|
        // +---------------+----+
    }

    /**
     * https://docs.databricks.com/spark/latest/data-sources/sql-databases.html
     * https://spark.apache.org/docs/latest/sql-programming-guide.html     *
     */
    private static void runJdbcDatasetExample(SparkSession spark) {
        final String DRIVER = "org.sqlite.JDBC";
        final String URL = "jdbc:sqlite:." + File.separator + "data" + File.separator + "sample.db";
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("url", URL)
                .option("dbtable", "BOREHOLES_POSITION")
                .option("user", "")
                .option("password", "")
                .load();

        jdbcDF.show();

//        +-----+-----------+-----------+------+
//                |BH_ID|       BH_X|       BH_Y|  BH_Z|
//                +-----+-----------+-----------+------+
//                |  Z01|4955.607439|45285.05923|1056.3|
//|  Z02|4980.230737|45282.35419|1069.5|
//|  Z03|5005.431813|45279.55407|1080.0|
//|  Z04|5026.899397|45272.27376|1085.1|
//|  Z10|5042.766741|45246.51266|1098.0|
//|  Z11|5038.846574|  45238.859|1094.3|
//|  Z05|4989.057676|45267.75638|1066.0|
//|  Z09|5000.929017|45246.29973|1076.1|
//|  Z06| 4969.73758|45248.72796|1067.7|
//|  Z07|4960.170505|45240.89638|1064.1|
//|  Z08|4956.367738|45225.12018|1062.9|
//|  Z16|4962.055481|45202.89788|1070.3|
//|  Z19|4956.148979|45188.44153|1075.2|
//|  Z21|4956.969326|45173.47475|1081.7|
//|  Z27|4965.974919|45140.47855|1087.5|
//|  Z22|4982.983458|45167.55002|1093.0|
//|  Z24|4990.640035|45163.06545|1097.6|
//|  Z18| 5042.65007|45193.34539|1101.4|
//|  Z26|5043.342808|45160.22158|1108.6|
//|  Z28|5044.691824|45138.50971|1116.3|
//                +-----+-----------+-----------+------+

//        Properties connectionProperties = new Properties();
//        connectionProperties.put("user", "");
//        connectionProperties.put("password", "");
//
//        Dataset<Row> jdbcDF2 = spark.read()
//                .jdbc(URL, "schema.tablename", connectionProperties);
//
//        // Saving data to a JDBC source
//        jdbcDF.write()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql:dbserver")
//                .option("dbtable", "schema.tablename")
//                .option("user", "username")
//                .option("password", "password")
//                .save();
//
//        jdbcDF2.write()
//                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
//
//        // Specifying create table column data types on write
//        jdbcDF.write()
//                .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
//                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);

    }

    public static class Square implements Serializable {
        private int value;
        private int square;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getSquare() {
            return square;
        }

        public void setSquare(int square) {
            this.square = square;
        }
    }


    public static class Cube implements Serializable {
        private int value;
        private int cube;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getCube() {
            return cube;
        }

        public void setCube(int cube) {
            this.cube = cube;
        }
    }
}
