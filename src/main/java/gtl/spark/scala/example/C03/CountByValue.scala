package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 计算数据集中某个数据出现的个数，并将其以map的形式返回。
  **/
object CountByValue {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("CountByValue ")
    val sc = new SparkContext(conf)
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6, 3, 4, 5, 5, 6, 7))
    val result = arr.countByValue()
    result.foreach(println)
  }
}