package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 提前计算的cache方法
  * cache方法的作用就是将数据内容计算并保存在计算节点的内存中。这个方法的使用是针对Spark的Lazy数据处理模式
  * 在Lazy模式中，数据在编译和未使用的时候是不进行计算的，而仅仅保存其存储地址，只有在Action方法到来的时候才正式计算。
  * 这样做的好处就是可以极大地减少存储空间，从而提高利用了，而有时必须要求数据进行计算，此时就需要使用cache方法
  **/
object Cache {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Cache")
    val sc = new SparkContext(sparkConf)
    val arr = sc.parallelize(Array("abc", "b", "c", "d", "e", "f"))
    println(arr)
    println("------------------")
    println(arr.cache()) //这里需要理解cache、persist、checkpoint等基本概念
    println("------------------")
    arr.foreach(println) //专门用来打印为进行Action操作的数据的专用方法，额可以对数据进行提早计算
  }
}
