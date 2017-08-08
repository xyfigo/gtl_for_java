package gtl.spark.scala.example.C13

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 对数据进行了基本统计量方面的分析，分别从微观角度对数据集内部进行分析计算，在宏观方面对不同的数据集进行分析，并且通过相关系数方法是可能含有重复项进行分析。
  *
  * 此程序将对数据集进行进一步的分析，综合运用回归方法是对数据进行统计分析。此项分析可以对数据集的你和程度和趋势做出相关研究
  **/
/**
  * http://blog.csdn.net/tuntunwang/article/details/60870312
  *
  * */
object irisLinearRegression {
  def main(args: Array[String]) {

    // 屏蔽不必要的日志显示终端上  设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisLinearRegression ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\Sepal.length.width_setosa.txt") //读取数据
    val parsedData = data.map { line => //处理数据
      val parts = line.split('	') //按空格分割   //注意程序中的分隔符和数据中的分隔符要对应统一  否则会存在问题
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).toDouble)) //固定格式
    }.cache() //加载数据
    val model = LinearRegressionWithSGD.train(parsedData, 10, 0.1) //创建模型
    println("回归公式为: y = " + model.weights + " * x + " + model.intercept) //   回归公式为: y = [1.4566650398487002] * x + 0.0
  }
}
