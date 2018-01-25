package gtl.spark.scala.example.API

import org.apache.spark.sql.SparkSession

object NewAggregate {
  def main(args: Array[String]): Unit = {
    val sc = SparkSession
      .builder()
      // .master("spark://192.168.0.130:7077")
      .master("local")
      .appName("Aggegate")
      .getOrCreate()
      .sparkContext
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6), 3)
    val result = arr.aggregate(0)(math.max(_, _), _ + _)
    println(result)
  }
}
