package gtl.spark.kotlin.example.rdd

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object Coalesce {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Coalesce")
        val sc = JavaSparkContext(sparkConf)
        val al = listOf<Int>(1, 2, 3, 4, 5, 6)
        val rdd = sc.parallelize(al)
        var r: Int? = rdd.aggregate(0, { v1, v2 -> if (v1 > v2) v1 else v2 }, { v1, v2 -> v1!! + v2!! })
        println(r)
        val rdd2 = rdd.coalesce(2, true)
        r = rdd2.aggregate(0, { v1, v2 -> if (v1 > v2) v1 else v2 }, { v1, v2 -> v1!! + v2!! })
        println(r)
    }
}
