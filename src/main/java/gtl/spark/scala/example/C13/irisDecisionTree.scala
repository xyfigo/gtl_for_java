package gtl.spark.scala.example.C13

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 通过训练决策树，使计算机在非人工敢于的情况下对数据进行分类，并且可以直接打印出分类的结果
  * 决策树是一种畅通的数据挖掘的额方法，它用阿里研究特征数据的信息熵的大小，从而确定在数据决策的过程中那些数据起到一个决定性的作用
  *
  * 首先是对数据进行处理，决策树的数据处理需要标注数据的类别
  *
  * 决策树可以对输入的数据进行判定，并且打印其所属的归类，这一点相比较其他的方法来说是一个重大的进步，ta使得决策程序在完全没有人工干扰的情况下自助对数据进行分类
  * 这点极大地方便了大数据的决策与了分类的自动化处理
  * */
object irisDecisionTree {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisDecisionTree ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = MLUtils.loadLibSVMFile(sc, "c://a.txt") //输入数据集
    val numClasses = 3 //设定分类数量
    val categoricalFeaturesInfo = Map[Int, Int]() //设定输入格式
    val impurity = "entropy" //设定信息增益计算方式
    val maxDepth = 5 //设定树高度
    val maxBins = 3 //设定分裂数据集
    val model = DecisionTree.trainClassifier(data, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins) //建立模型
    val test = Vectors.dense(Array(7.2, 3.6, 6.1, 2.5))
   // println(model.predict("预测结果是:" + test))
  }
}
