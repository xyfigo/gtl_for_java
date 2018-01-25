import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 决策树是一种监管学习，所谓监管学习就是给定一堆样本，每个样本都有一组属性和一个类别，这些类别是事先确定的，那么通过学习可以得到一个分类器
  * ，这个分类器能够对新出现的对象给出正确的分类。目前决策树是分类算中应用比较多的算法之一，其原理是从一组无序无规律的因素中归纳总结出符合要求的分类规则
  *
  * 任何一个只要符合key-value模式的分类数据都可以根据决策树进行推断。
  *
  * 随机雨林顾名思义是决策树的一种大规模应用形式，其充分利用了大规模计算机并发计算的优势，可以对数据进行并行地处理和归纳分类
  * */
object DT {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("DT") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = MLUtils.loadLibSVMFile(sc, "D:\\devs\\data\\spark\\D08\\DTree.txt") //输入数据集

    val numClasses = 2 //设定分类数量
    val categoricalFeaturesInfo = Map[Int, Int]() //设定输入格式
    val impurity = "entropy" //设定信息增益计算方式
    val maxDepth = 5 //设定树高度
    val maxBins = 3 //设定分裂数据集

    val model = DecisionTree.trainClassifier(data, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins) //建立模型
    println(model.topNode) //打印决策树信息

  }
}
