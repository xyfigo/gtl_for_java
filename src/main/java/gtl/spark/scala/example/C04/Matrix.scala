package gtl.spark.scala.example.C04

import org.apache.spark.mllib.linalg.Matrices

/**
  * 大数据运算中，为了更好地提升计算效率，可以更多地使用矩阵运算进行数据处理。
  *
  * 一般来说，采用分布式矩阵进行存储的情况都是数据量非常大的，其处理速度和效率与其存储格式息息相关。MLlib提供了四种分布式矩阵存储形式，均由支持长整型的
  * 行列数和双精度的浮点型的数据内容构成。这四种矩阵分别为：行矩阵、带有行索引的行矩阵、坐标矩阵和块矩阵
  **/

// http://spark.apache.org/docs/latest/mllib-data-types.html

object Matrix {
  def main(args: Array[String]) {
    val mx = Matrices.dense(2, 3, Array(1, 2, 3, 4, 5, 6)) //创建一个分布式矩阵  三个参数，第一个为行数，第二个为列数，第三个为传入的数据值
    println(mx) //打印结果
  }
}
