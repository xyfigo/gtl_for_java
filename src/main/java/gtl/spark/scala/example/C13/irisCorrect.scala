package gtl.spark.scala.example.C13

import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 在对一些数据问题的分析中，其数据的产生是带有一定的相关性的，例如，某个地区供水量和用水量呈现出一个拟合度较好线性关系，对ta进行分析的时候，往往只需要分析一个变量即可
  *
  * 本数据集也是如此，数据集合中的萼片长、宽、花瓣长、宽这些数据项目在分析中是否有重复性需要去除，可以通过计算这些数据项目相互之间的相关系数做出分析。如果相关系数超过阈值，
  * 则可以认定这些数据项目具有一定的相关性，从而可以在数据分析中作为额外项目去除
  * */

/**
  * 特征分析
  *
  * */
object irisCorrect {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisCorrect ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val dataX = sc.textFile("D:\\devs\\data\\spark\\Sepal.length_setosa.txt") //读取数据
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    val dataY = sc.textFile("D:\\devs\\data\\spark\\Sepal.width_setosa.txt") //读取数据
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    val correlation: Double = Statistics.corr(dataX, dataY) //计算不同数据之间的相关系数
    println(correlation) //  结果和书本上有些差别
  }
}
