package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import java.util.*

object Aggregate {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Aggredate")
        val sc = JavaSparkContext(sparkConf)
        val al = ArrayList<Int>(6)
        for (i in 0..5)
            al.add(i + 1)
        val rdd = sc.parallelize(al, 2)
        val r = rdd.aggregate(0, { v1, v2 -> if (v1 > v2) v1 else v2 }, { v1, v2 -> v1!! + v2!! })
        println(r)
    }
}
