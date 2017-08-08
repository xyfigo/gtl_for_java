package gtl.spark.scala.example.C05

import org.apache.spark._
import org.apache.spark.mllib.recommendation.{ALS, Rating}

/**
  * 协同过滤算法是最常用的推荐算法，主要有给予用户推荐算法和给予物品的推荐算法。
  * 推荐算法的基础就是基于两个对象之间的相关性
  * 距离计算：余弦、欧几里得、曼哈顿
  *
  * ALS(交替最小二乘法)
  *
  * 志趣相投：
  * 用特定的计算方法扫描和指定目标相同的已有用户，根据给定的相似度对用户进行相似度计算，选择最高分的的用户并根据已有的信息作为推荐结果从而反馈给用户
  * 物以类聚：
  * 对于不熟悉的用户，在缺少特定用户信息的情况下，根据用户已有的偏好数据去推荐一个未知物品是合理的。
  * 顾名思义，给予物品的推荐算法是以已有的物品为线索去进行相似度计算从而推荐给特定的目标用户
  * */
object CollaborativeFilter {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("CollaborativeFilter ") //设置环境变量
    val sc = new SparkContext(conf) //实例化环境
    val data = sc.textFile("d:\\devs\\data\\spark\\D05\\u1.txt") //设置数据集
    val ratings = data.map(_.split(' ') match { //处理数据
      case Array(user, item, rate) => //将数据集转化
        Rating(user.toInt, item.toInt, rate.toDouble) //将数据集转化为专用Rating   在java中需要自己去重新构建Rate类
    })
    val rank = 2 //设置隐藏因子
    val numIterations = 10 //设置迭代次数  //迭代50次就已经超出16G的内存了
    val model = ALS.train(ratings, rank, numIterations, 0.01) //进行模型训练
    var rs = model.recommendProducts(2, 2) //为用户2推荐2个商品  第一个参数是用户  第二个参数是推荐的数量
    rs.foreach(println) //打印结果   //Rating(2,15,3.941790867064295)   Rating(2,12,2.011578132531028)
  }
}
