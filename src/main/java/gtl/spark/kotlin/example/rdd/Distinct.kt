package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext


object Distinct {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Coalesce")
        val sc = JavaSparkContext(sparkConf)
        val slist = listOf("cool", "good", "bad", "fine", "cool", "good")
        val arr = sc.parallelize(slist).distinct()
        arr.foreach { s -> println(s) }
    }
}
