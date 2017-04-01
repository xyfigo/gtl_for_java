package cn.edu.cug.cs.kotlin.kt03

/**
 * Created by ZhenwenHe on 2017/4/1.
 */
class Name : Namable {
    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }

    override var firstName: String
        get() = firstName //To change initializer of created properties use File | Settings | File Templates.
        set(value) {
            this.firstName = value
        }
    override var lastName: String
        get() = lastName //To change initializer of created properties use File | Settings | File Templates.
        set(value) {
            this.lastName = value
        }

    override fun compareTo(other: Namable): Int {
        val i = other.firstName.compareTo(this.firstName)
        if (i == 0)
            return other.lastName.compareTo(this.lastName)
        return i
    }
}