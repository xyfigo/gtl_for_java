package gtl.spark.scala.example.C04

import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * 反映两个变量间线性相关关系的统计指标被称为相关系数。相关系数是一种用来反映变量之间相关关系密切程序的统计指标，在现实中一般用于对
  * 两组数据的拟合和相似程度进行定量化分析
  * 常用的一般是皮尔逊相关系数，在MLlib中默认的相关系数求发也是使用其。斯皮尔曼相关系数用的比较少，但是起能够较好地反映不同数据集的趋势程序，
  * 因此在实际应用中还是很有应用空间
  *
  * 需要明白，不同的相关系数有不同的代表意义。皮尔逊相关系数代表两组数据的余弦分开程度，表示随着数据量的增加，两组数据的差别将增大。
  * 而斯皮尔曼相关系数更注重两组数据的拟合程序，即两组数据岁数据量增加而增长曲线不变
  **/

object Correct {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("Correct") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val rddX = sc.textFile("D:\\devs\\data\\spark\\D04\\testCorrectX.txt") //读取数据
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    val rddY = sc.textFile("D:\\devs\\data\\spark\\D04\\testCorrectY.txt") //读取数据
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    val correlation: Double = Statistics.corr(rddX, rddY) //计算不同数据之间的相关系数
    println(correlation) //0.9999999999999998

    val correlation2: Double = Statistics.corr(rddX, rddY, "spearman") //使用斯皮尔曼计算不同数据之间的相关系数
    println(correlation2) //0.9999999999999998
  }
}
