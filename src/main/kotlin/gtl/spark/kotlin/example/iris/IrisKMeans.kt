package gtl.spark.kotlin.example.iris

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.DataTypes.DoubleType
import org.apache.spark.sql.types.DataTypes.IntegerType
import org.apache.spark.sql.types.StructField
import java.util.*

object IrisKMeans {
    var DATA_FILE = "d:\\devs\\data\\spark\\iris.csv"
    fun irisKMeans(
            spark: SparkSession,
            newDF: Dataset<Row>) {
        newDF.createOrReplaceTempView("Iris")
        val setosa = spark.sql(
                "SELECT SepalLength,SepalWidth,PetalLength,PetalWidth FROM Iris")
        val data = setosa
                .toJavaRDD()
                .map { r ->
                    Vectors.dense(
                            r.getDouble(0),
                            r.getDouble(1),
                            r.getDouble(2),
                            r.getDouble(3))
                }
        val model = KMeans.train(data.rdd(), 3, 20)
        val vv = model.clusterCenters()
        for (i in vv.indices) {
            println(vv[i])
        }
        println(
                model.predict(Vectors.dense(
                        5.4, 3.1, 2.8, 0.8)))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisKMeans")
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
        irisKMeans(spark, newDF)
    }
}
