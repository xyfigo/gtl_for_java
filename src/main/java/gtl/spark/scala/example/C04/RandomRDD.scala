package gtl.spark.scala.example.C04

import org.apache.spark.mllib.random.RandomRDDs._
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 随机数是统计分析中常用的一些数据文件，一般用来检验随机算法和执行效率等，在Scala和Java语言中都提供了大量的API，以随机生成各种形式的随机数。RDD也是如此
  * RandomRDDs类是随机数生成类
  **/
object RandomRDD {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("RandomRDD")
    val sc = new SparkContext(conf)
    val randomNum = normalRDD(sc, 100) //创建生成100个随机数
    randomNum.foreach(println)
  }
}
