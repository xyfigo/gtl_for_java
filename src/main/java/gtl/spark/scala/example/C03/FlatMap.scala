package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}

/**
  * flatMap()
  *
  * 以行为单位操作数据的flatMap方法
  * flatMap方法是对RDD中的数据集合进行整体操作的一个特殊方法，因为其在定义的时候就是针对数据集进行操作，因为最终返回的也是一个数据集
  **/

/**
  * collect()
  *
  * Return an array that contains all of the elements in this RDD.
  * This method should only be used if the resulting array is expected to be small, as
  * all the data is loaded into the driver's memory.
  */
object FlatMap {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("FlatMap()")
    val sc = new SparkContext(conf)
    var arr = sc.parallelize(Array(1, 2, 3, 4, 5))
    val result = arr.flatMap(x => List(x + 1)).collect()
    result.foreach(println)
  }
}


/**
  * output   和map()输出的结果还是存在一些差异
  * 2
  * 3
  * 4
  * 5
  * 6
  **/