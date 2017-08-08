package gtl.spark.scala.example.C04

import org.apache.spark._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix

/**
  * 行矩阵
  *
  * 最基本的一种矩阵类型。行矩阵是以行作为基本方向的矩阵存储格式，列的作用相对较小。可以将其理解为行矩阵是一个巨大的特征向量的集合。
  * 每一行就是一个具有相同格式的向量数据，且每一行的向量内容都可以单独取出来进行操作
  *
  * 注意：
  * 如果直接打印rm中的具体内容，最终结果显示是数据的内存地址。这表明RowMatrix在MLlib中仍然只是一个Transformation，并不是最终的运行结果
  **/
object C04RowMatrix {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("RowMatrix") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val rdd = sc.textFile("D:\\devs\\data\\spark\\D04\\RowMatrix.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转成Vector格式
    val rm = new RowMatrix(rdd) //读入行矩阵
    println(rm.numRows()) //2
    println(rm.numCols()) //3

    /**
      * 注意这里RowMatrix在Mllib中仍然只是一个Transformation。并不是最终的运行结果。也就是说没有进行action
      *
      **/
  }
}
