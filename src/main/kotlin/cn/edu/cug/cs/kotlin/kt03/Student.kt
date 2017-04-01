package cn.edu.cug.cs.kotlin.kt03

/**
 * Created by ZhenwenHe on 2017/3/29.
 */
class Student : Person {
    constructor(stuID: Long, name: Name, age: Int?) : super(name, age) {
        this.studentID = stuID
    }

    constructor(stuID: Long, firstName: String, lastName: String, age: Int?)
            : super(firstName, lastName, age) {
        this.studentID = stuID
    }

    constructor(stuID: Long, firstName: String, lastName: String)
            : super(firstName, lastName) {
        this.studentID = stuID
    }

    var studentID: Long
        get() = this.studentID
        set(value) {
            this.studentID = value
        }

}


fun main(args: Array<String>) {
//    val person1 = Person("Alex", "Smith", 29)
//    val person2 = Person("Jane", "Smith")
//    println("${person1.name.firstName},${person1.name.lastName} is ${person1.age} years old")
//    println("${person2.name.firstName},${person2.name.lastName} is ${person2.age?.toString() ?: "?"} years old")
//
}
