package cn.edu.cug.cs.kotlin.kt04

import cn.edu.cug.cs.kotlin.kt03.Student
import gtl.common.Property
import gtl.common.Variant
import java.util.*

/**
 * Created by ZhenwenHe on 2017/3/29.
 */
object KT04 {
    @JvmStatic fun main(args: Array<String>) {
        println("KHello")
        val s = Student(10L, "Vincen")
        println(s)
        s.name = "H"

        val vs = arrayOfNulls<Variant>(18)
        vs[0] = Variant(true)
        vs[1] = Variant('1'.toChar())
        vs[2] = Variant(1.toByte())
        vs[3] = Variant(1.toShort())
        vs[4] = Variant(1.toInt())
        vs[5] = Variant(1.toLong())
        vs[6] = Variant(1.toFloat())
        vs[7] = Variant(1.toDouble())
        vs[8] = Variant("1")
        val bb = BooleanArray(10)
        Arrays.fill(bb, true)
        vs[9] = Variant(bb)
        val cc = CharArray(10)
        Arrays.fill(cc, '1')
        vs[10] = Variant(cc)
        val ss = ShortArray(10)
        Arrays.fill(ss, 1.toShort())
        vs[11] = Variant(ss)
        val ii = IntArray(10)
        Arrays.fill(ii, 1.toInt())
        vs[12] = Variant(ii)
        val ff = FloatArray(10)
        Arrays.fill(ff, 1.toFloat())
        vs[13] = Variant(ff)
        val dd = DoubleArray(10)
        Arrays.fill(dd, 1.toDouble())
        vs[14] = Variant(dd)
        val yy = ByteArray(10)
        Arrays.fill(yy, 1.toByte())
        vs[15] = Variant(yy)
        val ll = LongArray(10)
        Arrays.fill(ll, 1.toLong())
        vs[16] = Variant(ll)
        val tt = arrayOfNulls<String>(10)
        Arrays.fill(tt, "1")
        vs[17] = Variant(tt)

        val tv = arrayOfNulls<Variant>(18)
        var i = 0
        for (v in vs) {
            tv[i] = v?.clone() as Variant
            println(tv[i].toString())
            i++

        }

        var ps = arrayOfNulls<Property>(18) as Array<Property>
        ps[0] = Property("1", 1.toByte() as Any)
        ps[1] = Property("2", '1' as Any)
        ps[2] = Property("3", true as Any)
        ps[3] = Property("4", 1.toShort() as Any)
        ps[4] = Property("5", 1.toInt() as Any)
        ps[5] = Property("6", 1L.toLong() as Any)
        ps[6] = Property("7", 1.0f.toFloat() as Any)
        ps[7] = Property("8", 1.0 as Any)
        ps[8] = Property("9", java.lang.String("1") as Any)
        val bs = ByteArray(10)
        Arrays.fill(bs, 1.toByte())
        ps[9] = Property("10", bs as Any)
        val cs = CharArray(10)
        Arrays.fill(cs, '1'.toChar())
        ps[10] = Property("11", cs as Any)
        val ss2 = ShortArray(10)
        Arrays.fill(ss2, 1.toShort())
        ps[11] = Property("12", ss as Any)
        val iss = IntArray(10)
        Arrays.fill(iss, 1.toInt())
        ps[12] = Property("13", iss as Any)
        val ls = LongArray(10)
        Arrays.fill(ls, 1L)
        ps[13] = Property("14", ls as Any)
        val fs = FloatArray(10)
        Arrays.fill(fs, 1.0f)
        ps[14] = Property("15", fs as Any)
        val ds = DoubleArray(10)
        Arrays.fill(ds, 1.0)
        ps[15] = Property("16", ds as Any)
        val bbs = BooleanArray(10)
        Arrays.fill(bbs, true)
        ps[16] = Property("17", bbs as Any)
        val ssv = arrayOfNulls<String>(10)
        Arrays.fill(ssv, "1")
        ps[17] = Property("18", ssv as Any)
    }
}