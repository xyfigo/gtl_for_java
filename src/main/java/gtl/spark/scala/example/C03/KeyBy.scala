package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 数据集中的每个个体数据增加一个key，从而可以与原来的个体数据形成键值对
  **/

object KeyBy {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("keyBy()")
    val sc = new SparkContext(conf)
    var str = sc.parallelize(Array("one", "two", "three", "four", "five"))
    val str2 = str.keyBy(word => word.size)
    str2.foreach(println)
  }
}

/**
  * output   这里是按照字符串的长度作为key
  * (3,one)
  * (3,two)
  * (5,three)
  * (4,four)
  * (4,five)
  **/
