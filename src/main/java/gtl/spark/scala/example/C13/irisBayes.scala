package gtl.spark.scala.example.C13

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 分类器---这里主要是先使用贝叶斯分类器
  *
  * 聚类回归有助于发现新的未经证实和发现的东西，而对于已经有所归类的数据集，其处理可能不会按照固定的模式去做。
  * 因此对于其进行分析就需要使用另外一种数据的分类方法，即数据的分类
  *
  * 除了贝叶斯分类器，还有一种分类器叫做支持向量机(SVM)
  *
  * */

object irisBayes {
  def main(args: Array[String]) {
    // 屏蔽不必要的日志显示终端上  设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisBayes") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = MLUtils.loadLabeledPoints(sc, "D:\\devs\\data\\spark\\output3.txt") //读取数据集  //数据集的分割符号存在一定问题   但是不妨碍主题思想的表达
    val model = NaiveBayes.train(data,1.0) //训练贝叶斯模型
    val test = Vectors.dense(7.3,2.9,6.3,1.8) //创建待测定数据
    //val result = model.predict("测试数据归属在类别:" + test) //打印结果
  }
}
