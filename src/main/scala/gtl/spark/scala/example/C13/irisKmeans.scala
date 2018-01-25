package gtl.spark.scala.example.C13

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 分类和聚类是数据挖掘中常用的处理方法，他根据不同数据距离的大小从而决定出所属的类别
  *
  * 聚类分析是无监督学习的一种，他通过机器相处理，自行研究算去发现数据集的额潜在关系，并将关系最相近的部分结合在一起，从而实现对数据的聚类处理。聚类分析的最大特地那就是没有
  * 必然性，可能每次聚类处理的结果都是不相同的。
  * */

object irisKmeans {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisKmeans ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    //val data = MLUtils.loadLibSVMFile(sc,"D:\\devs\\data\\spark\\all.txt") //输入数据集   //要是使用loadSVMFile就不能使用split方法
    val data = sc.textFile("D:\\devs\\data\\spark\\all.txt") //输入数据集
    val parsedData = data.map(s => Vectors.dense(s.split('	').map(_.toDouble))).cache() //数据处理
    val numClusters = 3 //最大分类数
    val numIterations = 20 //迭代次数
    val model = KMeans.train(parsedData, numClusters, numIterations) //训练模型
    model.clusterCenters.foreach(println) //打印中心点坐标
  }
}

/**
  * [6.853846153846153,3.0769230769230766,5.715384615384615,2.053846153846153]
  * [5.005999999999999,3.4180000000000006,1.4640000000000002,0.2439999999999999]
  * [5.88360655737705,2.7409836065573776,4.388524590163936,1.4344262295081969]
  *
  **/