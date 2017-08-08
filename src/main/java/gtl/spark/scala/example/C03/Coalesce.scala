package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Coalesce方法是将已经存储的数据重新分片后再进行存储
  * 第一个参数是将数据重新分片的片数，布尔参数指的是将数据分成更小的片时使用
  **/
object Coalesce {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Coalesce ")
    val sc = new SparkContext(conf)
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6))
    val arr2 = arr.coalesce(2, true)
    val result = arr.aggregate(0)(math.max(_, _), _ + _) //6
    println(result)
    val result2 = arr2.aggregate(0)(math.max(_, _), _ + _) //11 ？  //理解具体分区过程 窄依赖 宽依赖
    println(result2)
  }
}
