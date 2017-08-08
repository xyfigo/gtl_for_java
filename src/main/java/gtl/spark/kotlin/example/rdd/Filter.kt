package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object Filter {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Filter")
        val sc = JavaSparkContext(sparkConf)
        val ilist = listOf(1, 2, 3, 4, 5)
        val arr = sc.parallelize(ilist)                             //创建数据集
        val result = arr.filter { v -> v >= 3 }                                        //进行筛选工作
        result.foreach { s -> println(s) }
    }
}
