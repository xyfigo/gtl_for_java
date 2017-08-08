package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

object Repartition {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Repartition ")
    val sc = new SparkContext(conf)
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6))
    val arr2 = arr.repartition(3)
    println(arr2.partitions.length) //3
  }
}
