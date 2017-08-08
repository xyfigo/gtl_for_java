package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 去除数据集中重复的项
  **/
object Distinct {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Distinct ")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array(("cool"), ("good"), ("bad"), ("fine"), ("good"), ("cool")))
    val result = arr.distinct()
    result.foreach(println)
  }
}
