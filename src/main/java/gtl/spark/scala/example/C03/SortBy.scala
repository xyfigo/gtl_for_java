package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * SortBy()
  *
  * 对已有的RDD重新排序，并将重新排序以后的数据生成一个新的RDD
  * 主要是有三个参数，第一个是传入的方法，用以计算排序的数据dirge是制定排序的值是按照升序还是降序显示。第三个是分片的数量
  **/

object SortBy {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("SortBy")
    val sc = new SparkContext(conf)
    var str = sc.parallelize(Array((5, "b"), (6, "a"), (1, "f"), (3, "d"), (4, "c"), (2, "e")))
    str = str.sortBy(word => word._1, true) //(1,f)(2,e)(3,d)(4,c)(5,b)(6,a)
    val str2 = str.sortBy(word => word._2, true) //(6,a)(5,b)(4,c)(3,d)(2,e)(1,f)
    str.foreach(print)
    str2.foreach(print)
  }
}

/**
  * 相关说明，"._1"的格式是元祖中数据符号的表示方法，意思是使用当前遇上元组中的第一个数据，同样的表示，“._2”是使用元祖中第二个数据
  **/