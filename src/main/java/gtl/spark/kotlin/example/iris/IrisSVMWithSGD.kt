package gtl.spark.kotlin.example.iris

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.DataTypes.DoubleType
import org.apache.spark.sql.types.DataTypes.IntegerType
import org.apache.spark.sql.types.StructField
import java.util.*

object IrisSVMWithSGD {
    fun irisSVMWithSGD(
            spark: SparkSession,
            newDF: Dataset<Row>
    ) {
        println(spark)
        val data = newDF.toJavaRDD()
                .map { r ->
                    LabeledPoint(
                            (if (r.getInt(0) == 3) 1 else 0).toDouble(), //如果是virginica设置为1，否则设置为0
                            Vectors.dense(
                                    r.getDouble(1),
                                    r.getDouble(2),
                                    r.getDouble(3),
                                    r.getDouble(4)))
                }
        val svmModel = SVMWithSGD.train(data.rdd(), 50)
        println("[7.2,2.8,6.2,1.9]是否属于virginica？ " + svmModel.predict(Vectors.dense(7.2, 2.8, 6.2, 1.9)))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisSVMWithSGD")
                .orCreate
        val df = spark.read().csv(
                "d:\\devs\\data\\spark\\iris.csv")
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
        irisSVMWithSGD(spark, newDF)
    }
}
