package gtl.spark.kotlin.example.iris

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.IsotonicRegression
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.DataTypes.DoubleType
import org.apache.spark.sql.types.DataTypes.IntegerType
import org.apache.spark.sql.types.StructField
import scala.Tuple2
import scala.Tuple3
import java.util.*

object IrisIsotonicRegression {
    var DATA_FILE = "d:\\devs\\data\\spark\\iris.csv"
    fun isotonicRegression(
            spark: SparkSession,
            newDF: Dataset<Row>) {
        //计算setosa的萼片长度与宽度的相关系数
        newDF.createOrReplaceTempView("Iris")
        val setosa = spark.sql(
                "SELECT SepalLength,SepalWidth FROM Iris where Species=1")
        val data = setosa
                .toJavaRDD()
                .map { r ->
                    Tuple3(
                            r.getDouble(0), r.getDouble(1), 1.0)
                }
        val ir = IsotonicRegression()
        ir.setIsotonic(true)
        val model = ir.run(data)
        //构建真实数据与预测数据的RDD
        val realAndPredictValues = data.map { t ->
            Tuple2(
                    t._1(), model.predict(t._2()))
        }
        //计算均方误差
        val MSE = realAndPredictValues.map { t -> Vectors.dense(Math.pow(t._1() - t._2(), 2.0)) }
        println(
                "setosa萼片长度与宽度的保序回归的均方误差为：" + Statistics.colStats(MSE.rdd()).mean())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisIsotonicRegression")
                .orCreate
        val df = spark.read().csv(DATA_FILE)
        val lines = df.toJavaRDD().map { r ->
            var species: Int
            if (r.getString(4).compareTo("Iris-virginica") == 0)
            //1
                species = 3
            else if (r.getString(4).compareTo("Iris-versicolor") == 0)
            //2
                species = 2
            else if (r.getString(4).compareTo("Iris-setosa") == 0)
            //3
                species = 1
            else
                species = 0
            RowFactory.create(species,
                    java.lang.Double.valueOf(r.getString(0)),
                    java.lang.Double.valueOf(r.getString(1)),
                    java.lang.Double.valueOf(r.getString(2)),
                    java.lang.Double.valueOf(r.getString(3)))
        }
        //建立数据框架的结构
        val species = DataTypes.createStructField(
                "Species", IntegerType, false)
        val sepalLength = DataTypes.createStructField(
                "SepalLength", DoubleType, false)
        val sepalWidth = DataTypes.createStructField(
                "SepalWidth", DoubleType, false)
        val petalLength = DataTypes.createStructField(
                "PetalLength", DoubleType, false)
        val petalWidth = DataTypes.createStructField(
                "PetalWidth", DoubleType, false)
        val sfs = ArrayList<StructField>(5)
        sfs.add(species)
        sfs.add(sepalLength)
        sfs.add(sepalWidth)
        sfs.add(petalLength)
        sfs.add(petalWidth)
        val st = DataTypes.createStructType(sfs)
        //创建新的数据集
        val newDF = spark.createDataFrame(lines, st)
        isotonicRegression(spark, newDF)
    }
}
