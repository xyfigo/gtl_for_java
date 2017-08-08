package gtl.spark.scala.example.C04

import org.apache.spark.mllib.linalg.{Matrices, Vectors}
import org.apache.spark.mllib.stat.Statistics


/**
  * 卡方检验是一种常用的假设检验方法，能够较好地对数据集之间的拟合度、相关性和独立性进行验证。MLlib规定常用的卡方检验使用的数据集
  * 一般为向量和矩阵。
  *
  * 输出结果包含三个数据，分别为自由度、P值以及统计量
  * 自由度  总体参数估计量中变量值独立自由边数的数目
  * 统计量 不同方法下的统计量
  * P值  显著性差异指标
  * 方法 卡方检验使用方法
  * 一般情况下，P<0.05指的是数据集不存在显著性差异
  **/
object ChiSq {
  def main(args: Array[String]) {
    val vd = Vectors.dense(1, 2, 3, 4, 5)
    val vdResult = Statistics.chiSqTest(vd)
    println(vdResult)
    println("-------------------------------")
    val mtx = Matrices.dense(3, 2, Array(1, 3, 5, 2, 4, 6))
    val mtxResult = Statistics.chiSqTest(mtx)
    println(mtxResult)
  }
}


/**
  * Chi squared test summary:
  * method: pearson
  * degrees of freedom = 4
  * statistic = 3.333333333333333
  * pValue = 0.5036682742334986
  * No presumption against null hypothesis: observed follows the same distribution as expected..
  * -------------------------------
  * Chi squared test summary:
  * method: pearson
  * degrees of freedom = 2
  * statistic = 0.14141414141414144
  * pValue = 0.931734784568187
  * No presumption against null hypothesis: the occurrence of the outcomes is statistically independent..
  *
  *
  **/