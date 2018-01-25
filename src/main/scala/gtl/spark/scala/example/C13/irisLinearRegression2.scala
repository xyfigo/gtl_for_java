package gtl.spark.scala.example.C13

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 需要对回归方程进行验证，那么最简单的一个方法就是返回计算相关的变量，判断其拟合程度.这里可以使用MLlib自带的均方误差(MSE)判断方法对其进行判断。
  *
  * http://blog.selfup.cn/747.html
  * http://cwiki.apachecn.org/pages/viewpage.action?pageId=2886830
  * https://docs.microsoft.com/en-us/azure/machine-learning/team-data-science-process/scala-walkthrough
  *
  **/

object irisLinearRegression2 {
  def main(args: Array[String]) {
    // 屏蔽不必要的日志显示终端上  设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisLinearRegression2") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\Sepal.length.width_setosa.txt") //读取数据
    val parsedData = data.map { line => //处理数据
      val parts = line.split('	') //按空格分割
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).toDouble)) //固定格式
    }.cache() //加载数据
    val model = LinearRegressionWithSGD.train(parsedData, 10, 0.1) //创建模型
    val valuesAndPreds = parsedData.map { point => { //创建均方误差训练数据
      val prediction = model.predict(point.features) //创建数据
      (point.label, prediction) //创建预测数据
    }
    }
    val MSE = valuesAndPreds.map { case (v, p) => math.pow((v - p), 2) }.mean() //计算均方误差
    println("均方误差结果为: " + MSE) //打印结果
  }
}
