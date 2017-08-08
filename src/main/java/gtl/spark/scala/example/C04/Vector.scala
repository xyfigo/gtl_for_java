package gtl.spark.scala.example.C04

import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
  * Mllib使用的本地化存储类型是向量，这里的向量主要是由两类构成：稀疏型数据集(spares)和密集型数据集(dense).
  *
  * sparse()
  *
  * 将给定的数据分解成X个部分进行处理 其对应值分别属于程序中vs的向量对应值
  * 三个参数
  * 第一个代表输入数据的大小，一般要求大于等于输入的数据值
  * 第二个参数是数据vs下标的数值
  * 第三个是输入的数据值，这里一般要求将其作为一个Array类型的数据进行输入
  **/
object C04Vector {
  def main(args: Array[String]) {
    val vd: Vector = Vectors.dense(1, 0, 3) //建立密集向量  数据集被作为一个集合的形式整体存储  可以理解为mllib专用的一种集合形式，它与Array类似，最终显示结果和方法调用类似
    println(vd(2)) //3.0  //下标从0开始
    val vs: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(9, 5, 2, 7)) //建立稀疏向量
    println(vs(2)) //2.0
  }
}

/**
  * MLlib目前仅支持整数与浮点数。其他类型的数据类型均不在支持范围之内，这也和MLlib的主要用途相关，其主要的目的就是用于数值计算
  *
  **/
