package gtl.spark.scala.example.API

import org.apache.spark.{SparkConf, SparkContext}


//object WordCount {
//  def main(args:Array[String]): Unit ={
//    val conf= new SparkConf().setMaster("local").setAppName("WordCount")
//    val sc = new SparkContext(conf)
//    val data = sc.textFile("D:\\devs\\data\\spark\\wc.txt")
//    data.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect().foreach(println)
//  }
//}

object wordCount {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("spark://192.168.0.130:7077").setAppName("wordCount")
    //var jars = List{"/code/sbttest/out/artifacts/sbttest_jar/sbttest.jar"};
    val jars = List {
      "hdfs://192.168.0.130:9000/data/spark/sbttest.jar"
    };
    conf.setJars(jars)
    val sc = new SparkContext(conf) //创建环境变量实例 准备开始任务
    val data = sc.textFile("hdfs://192.168.0.130:9000/data/spark/wc.txt") //读取文件
    println(data.count()) //4
    // data.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect().foreach(println) //word计数
  }
}