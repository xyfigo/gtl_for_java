package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 对数据集进行过滤
  **/
object Filter {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Filter ")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array(1, 2, 3, 4, 5))
    val result = arr.filter(_ >= 3) //这里强调Scala编程中的编程规范，下划线_是作为占位符号标记所有的传过来的数据，在此犯法噶中，数组的数据(1,2,3,4,5)依次传进来替代列占位符号
    result.foreach(println)
  }
}
