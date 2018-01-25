package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

object Map {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Map ")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array(1, 2, 3, 4, 5))
    val result = arr.map(x => List(x + 1)).collect()
    result.foreach(println)
  }
}

/**
  * output
  * List(2)
  * List(3)
  * List(4)
  * List(5)
  * List(6)
  **/