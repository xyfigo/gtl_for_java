package gtl.spark.scala.example.C13

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 逻辑回归不是回归算法
  *
  * 使用逻辑回归一样能够获得回归公式和均方误差。
  *
  * 回归主要是一元为主，而逻辑回归更胜于使用在多元线性回归的分析中，因此可能造成使用逻辑回归后的均方差升高
  *
  * 回归分析方法被广发地应用于解释特性之间的相互依赖关系。把两个或者是两个以上定距或者是定比例的数量关系用函数形式表示出来，就是回归分析要解决的问题。回归分析是一种非常有用且灵活的分析方法
  *
  *
  * */
object irisLogicRegression {
  def main(args: Array[String]) {

    // 屏蔽不必要的日志显示终端上  设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisLogicRegression ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\Sepal.length.width_setosa.txt") //读取数据
    val parsedData = data.map { line => //处理数据
      val parts = line.split('	') //按空格分割
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).toDouble)) //固定格式
    }.cache() //加载数据
    val model = LogisticRegressionWithSGD.train(parsedData, 20) //创建模型
    val valuesAndPreds = parsedData.map { point => { //创建均方误差训练数据
      val prediction = model.predict(point.features) //创建数据
      (point.label, prediction) //创建预测数据
    }
    }
    val MSE = valuesAndPreds.map { case (v, p) => math.pow((v - p), 2) }.mean() //计算均方误差
    println("均方误差结果为:" + MSE
    ) //打印结果
  }
}

