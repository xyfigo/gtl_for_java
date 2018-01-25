package gtl.spark.scala.example.C04

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 均值和标准差
  * 距离计算：欧几里得距离、曼哈顿距离、余弦距离
  **/
object Summary {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("Summary") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val rdd = sc.textFile("D:\\devs\\data\\spark\\D04\\testSummary.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转成Vector格式
    val summary = Statistics.colStats(rdd) //获取Statistics实例
    println(summary.mean) //计算均值   3.0
    println(summary.variance) //计算标准差   2.5
    println(summary.normL1) //计算曼哈段距离   15.0
    println(summary.normL2) //计算欧几里得距离   7.416198487095663
  }
}
