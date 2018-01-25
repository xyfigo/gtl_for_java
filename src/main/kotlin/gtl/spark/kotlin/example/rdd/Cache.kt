package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object Cache {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Cache")
        val sc = JavaSparkContext(sparkConf)
        val alist = listOf<String>("abc", "b", "c", "d", "e", "f")
        val rdd = sc.parallelize(alist)
        println(rdd)
        println("---------------")
        println(rdd.cache())
        println("---------------")
        rdd.foreach { s -> println(s) }
    }
}