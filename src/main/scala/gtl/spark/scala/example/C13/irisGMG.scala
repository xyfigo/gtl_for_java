package gtl.spark.scala.example.C13

import org.apache.spark.mllib.clustering.GaussianMixture
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 使用高斯聚类器
  **/

object irisGMG {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("irisGMG") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\all.txt") //输入数个
    val parsedData = data.map(s => Vectors.dense(s.trim.split('	') //转化数据格式
      .map(_.toDouble))).cache()
    val model = new GaussianMixture().setK(2).run(parsedData) //训练模型
    for (i <- 0 until model.k) {
      println("weight=%f\nmu=%s\nsigma=\n%s\n" format //逐个打印单个模型
        (model.weights(i), model.gaussians(i).mu, model.gaussians(i).sigma)) //打印结果
    }
  }
}

/**
  * weight=0.666672
  * 17/12/25 15:24:25 INFO SparkContext: Invoking stop() from shutdown hook
  * mu=[6.26198680130625,2.8719957524299398,4.905972981037261,1.675989681982419]
  * sigma=
  *0.4349759857773134   0.12094258101075123  0.4488722153772077   0.16550492798292002
  *0.12094258101075123  0.1096176177492205   0.14138226056833647  0.07923325049211286
  *0.4488722153772077   0.14138226056833647  0.6748563385532793   0.2858790491914481
  *0.16550492798292002  0.07923325049211286  0.2858790491914481   0.17863686500317574
  * *
  * weight=0.333328
  * mu=[5.006007568414766,3.4180166807955,1.4640024377335454,0.24399916839492178]
  * sigma=
  *0.12176199365581446   0.09828503575375239   0.015814998586706642  0.010336574428563677
  *0.09828503575375239   0.1422595075780037    0.011445440158347416  0.01120909834148338
  *0.015814998586706642  0.011445440158347416  0.029504037135692333  0.005584219578084808
  *0.010336574428563677  0.01120909834148338   0.005584219578084808  0.011264120800100318
  **/