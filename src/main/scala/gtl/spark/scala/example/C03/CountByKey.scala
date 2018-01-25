package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 计算数组中元数据键值对key出现的个数
  **/
object CountByKey {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("CountByKey ")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array((1, "cool"), (2, "good"), (1, "bad"), (1, "fine")))
    val result = arr.countByKey()
    result.foreach(println)
  }
}
