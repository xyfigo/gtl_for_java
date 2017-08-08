package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Reduce()
  *
  * RDD中一个较为重要的数据处理方法，与map方法不同之处在于，它在处理数据的时候需要两个参数
  *
  **/
object Reduce {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Reduce ")
    val sc = new SparkContext(conf)
    var str = sc.parallelize(Array("one", "two", "three", "four", "five"))
    val result = str.reduce(_ + _)
    result.foreach(print) //onetwothreefourfive


    val result2 = str.reduce(myFun) //自定义方法
    result2.foreach(print) //three
  }

  def myFun(str1: String, str2: String): String = { //创建方法
    var str = str1 //设置确定方法
    if (str2.size >= str.size) { //比较长度
      str = str2 //替换
    }
    return str //返回最长的那个字符串
  }
}

/**
  * output
  * onetwothreefourfive
  *
  * 从结果上面可以看到，reduce的方法其实主要是对传入的数据进行合并的处理。它的两个下划线分别代表的不同的内容，第一个下划线代表数据集的第一个数据
  * 而第二个下划线代表在第一个合并处理时代表空集
  **/