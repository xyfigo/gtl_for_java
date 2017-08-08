package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * groupBy()
  *
  * 将传入的数据进行分组，其分组的一句是作为参数传入的计算方法
  * 第一个参数是传入的方法名称，第二个参数是分组的标签值
  **/

object GroupBy {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("GroupBy ")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array(1, 2, 3, 4, 5))
    arr.groupBy(myFilter(_), 1)
    arr.groupBy(myFilter2(_), 2)
    //println(arr)  因为没有Action所以无法输出结果
    arr.foreach(println)
  }

  def myFilter(num: Int): Unit = { //自定义方法
    num >= 3 //条件
  }

  def myFilter2(num: Int): Unit = { //自定义方法
    num < 3 //条件
  }

}
