package cn.edu.cug.cs.kotlin.kt03

/**
 * Created by ZhenwenHe on 2017/4/1.
 */
interface Namable : Comparable<Namable> {
    var firstName: String
    var lastName: String
}