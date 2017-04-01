package cn.edu.cug.cs.kotlin.kt03

/**
 * Created by ZhenwenHe on 2017/4/1.
 */
open class Person constructor(var name: Namable, val age: Int?) {
    init {
        if (age != null) {
            require(age >= 0 && age < 150) { "Invalid age argument." }
        }
        this.name = name
    }

    constructor(firstName: String, lastName: String, age: Int?) : this(Name(firstName, lastName), age)
    constructor(firstName: String, lastName: String) : this(Name(firstName, lastName), null)

}
