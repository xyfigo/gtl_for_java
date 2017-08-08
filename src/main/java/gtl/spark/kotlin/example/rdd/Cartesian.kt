package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import java.util.*

object Cartesian {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Cartesian")
        val sc = JavaSparkContext(sparkConf)
        val i1list = ArrayList<Int>(6)
        val i2list = ArrayList<Int>(6)
        for (i in 0..5) {
            i1list.add(i + 1)
            i2list.add(6 - i)
        }
        val rdd1 = sc.parallelize(i1list)
        val rdd2 = sc.parallelize(i2list)
        val result = rdd1.cartesian(rdd2)
        result.foreach { s -> println(s) }
    }
}
