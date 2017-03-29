package cn.edu.cug.cs.kotlin.kt03

/**
 * Created by ZhenwenHe on 2017/3/29.
 */
object  KHello {
    @JvmStatic fun main(args: Array<String>) {
        println("KHello")
        val s = Student(10L, "Vincen")
        println(s)
    }
}
