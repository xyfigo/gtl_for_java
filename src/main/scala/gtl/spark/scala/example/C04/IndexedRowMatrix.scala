package gtl.spark.scala.example.C04

import org.apache.spark._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{IndexedRow,IndexedRowMatrix}

/**
  * 带有行索引的行矩阵
  *
  * 在RowMatrix程序中可以得知，单纯的航矩阵对其内容无法进行直接的显示，当然可以通过调用起方法显示内部数据内容，有时候为了方便在系统调试的过程中对航矩阵的内容
  * 进行观察和显示，MLlib提供另外一种矩阵形式，即带有行索引的行矩阵
  *
  * IndexedRowMatrix还有转换成其他矩阵的功能，例如ToRowmatrix将装换成成单纯的行矩阵，toCoordinateMatrix将其转换成坐标矩阵，toBlock将其转换成块矩阵
  *
  **/

object C04IndexedRowMatrix {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testIndexedRowMatrix") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val rdd = sc.textFile("D:\\devs\\data\\spark\\D04\\RowMatrix.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转化成向量存储
      .map((vd) => new IndexedRow(vd.size, vd)) //转化格式
    val irm = new IndexedRowMatrix(rdd) //建立索引行矩阵实例
    println(irm.getClass) //打印类型
    println(irm.rows.foreach(println)) //打印内容数据
  }
}

/**
  * IndexedRow(3,[1.0,2.0,3.0])
  * IndexedRow(3,[4.0,5.0,6.0])
  **/