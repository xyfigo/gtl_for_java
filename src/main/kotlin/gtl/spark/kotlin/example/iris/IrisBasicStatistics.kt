package gtl.spark.kotlin.example.iris

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.DataTypes.DoubleType
import org.apache.spark.sql.types.DataTypes.IntegerType
import org.apache.spark.sql.types.StructField
import java.util.*

object IrisBasicStatistics {
    var DATA_FILE = "d:\\devs\\data\\spark\\iris.csv"
    fun calculateMeanAndVariance(
            spark: SparkSession,
            newDF: Dataset<Row>) {
        //计算Setosa的萼片长度数据的均值与方差
        newDF.createOrReplaceTempView("Iris")
        val setosaSepalLengths = spark.sql(
                "SELECT SepalLength FROM Iris where Species=1")
        setosaSepalLengths.show()
        setosaSepalLengths.printSchema()
        val dv = setosaSepalLengths
                .toJavaRDD()
                .map { d -> Vectors.dense(d.getDouble(0)) }
        val s = Statistics.colStats(dv.rdd())
        println("setosa萼片长度数据个数:" + s.count())
        println("setosa萼片长度数据最大值:" + s.max())
        println("setosa萼片长度数据最小值:" + s.min())
        println("setosa萼片长度数据均值:" + s.mean())
        println("setosa萼片长度数据欧氏距离:" + s.normL1())
        println("setosa萼片长度数据曼哈顿距离:" + s.normL2())
        println("setosa萼片长度数据标准方差:" + s.variance())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisBasicStatistics")
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
        calculateMeanAndVariance(spark, newDF)
    }
}
