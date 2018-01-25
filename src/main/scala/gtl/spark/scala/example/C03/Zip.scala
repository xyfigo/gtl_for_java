package gtl.spark.scala.example

import org.apache.spark.{SparkConf, SparkContext}


/**
  * Zip()
  *
  * zip方法是常用的合并压缩算法，它可以将若干个RDD压缩成一个新的RDD，进而形成一系列键值对存储形式的新的RDD
  **/

object Zip {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("testZip")
    val sc = new SparkContext(conf)
    val arr1 = Array(1, 2, 3, 4, 5, 6)
    val arr2 = Array("a", "b", "c", "d", "e", "f")
    val arr3 = Array("g", "h", "i", "j", "k", "l")
    val arr4 = arr1.zip(arr2).zip(arr3)
    arr4.foreach(print) //((1,a),g)((2,b),h)((3,c),i)((4,d),j)((5,e),k)((6,f),l)  双重的键值对形式的数据
  }
}
