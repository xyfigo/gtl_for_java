package gtl.spark.scala.example


/**
  * Parallelized collections are created by calling SparkContext’s parallelize method on an existing collection in your driver program (a Scala Seq).
  * The elements of the collection are copied to form a distributed dataset that can be operated on in parallel.
  *
  * One important parameter for parallel collections is the number of partitions to cut the dataset into.
  *
  * Spark can create distributed datasets from any storage source supported by Hadoop, including your local file system, HDFS, Cassandra, HBase, Amazon S3, etc. Spark supports text files, SequenceFiles, and any other Hadoop InputFormat.
  **/

import org.apache.spark.{SparkConf, SparkContext}

object Aggregate {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Aggregate")
    val sc = new SparkContext(conf)
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6), 3) //创建RDD的三种方式，此方式为从已有的数据集合创建，第一个参数为数据，同时还有一个默认参数数值，初始值为1,表示的是将数据值分布在多少个数据节点中存放
    val result = arr.aggregate(2)(math.max(_, _), _ + _) //14
    println(result)


    //https://segmentfault.com/a/1190000007607506
    val rdd = List(1, 2, 3, 4)
    val input = sc.parallelize(rdd)
    val result2 = input.aggregate((0, 0))(
      (acc, value) => (acc._1 + value, acc._2 + 1),
      (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )
    val avg = result2._1 / result2._2
    println(avg) //2


    //String
    var arr2 = sc.parallelize(Array("abc", "b", "c", "d", "e", "f"))
    var result3 = arr.aggregate("g")((value, word) => value + word, _ + _) //gg12g34g56  ？？？
    println(result3)
  }
}
