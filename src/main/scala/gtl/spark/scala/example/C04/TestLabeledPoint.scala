package gtl.spark.scala.example.C04


import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 向量标签对于MLlib中机器学习算法的不同值做标记。例如在分类问题中，可以将不同的数据集分成若干份，以整形数0.1.2...进行标记，即程序
  * 的编写者可以根据自己的需要对数据进行标记
  *
  * LabeledPoint是建立向量标签的静态类，主要有两个方法，features用于显示打印标记点所代表的数据内容，而Label用于显示标记数
  * 除了使用以下两种方法建立向量标签，MLlib还支持直接从数据库中获取固定格式的数据集的方法
  **/
object TestLabeledPoint {
  def main(args: Array[String]) {
    val vd: Vector = Vectors.dense(2, 0, 6) //建立密集向量
    val pos = LabeledPoint(1, vd) //对密集向量建立标记点
    println(pos.features) //打印标记点内容数据     //[2.0,0.0,6.0]
    println(pos.label) //打印既定标记      //1.0
    val vs: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(9, 5, 2, 7)) //建立稀疏向量
    val neg = LabeledPoint(2, vs) //对密集向量建立标记点
    println(neg.features) //打印标记点内容数据   //(4,[0,1,2,3],[9.0,5.0,2.0,7.0])
    println(neg.label) //打印既定标记  //2.0


    // https://yq.aliyun.com/articles/5064
    //http://blog.csdn.net/illbehere/article/details/77162273
    //特征索引不能为0  再因为源代码已经做了减一操作 因此索引只能从1开始
    val conf = new SparkConf().setMaster("local").setAppName("TestLabeledPoint")
    //建立本地环境变量
    val sc = new SparkContext(conf) //建立Spark处理
    val mu = MLUtils.loadLibSVMFile(sc, "D:\\devs\\data\\spark\\D04\\loadLibSVMFile.txt")
    mu.foreach(println) //打印内容
  }
}
