package gtl.spark.scala.example.API

object Studies {

  def main(array: Array[String]): Unit = {
    val r = 1 to 10;
    var factor = 2
    val f = (i: Int) => i * factor
    val v1 = r.filter(_ % 2 == 0).map(f).reduce(_ * _);
    factor = 3
    val v2 = r.filter(_ % 2 == 0).map(f).reduce(_ * _);
    val v3 = m1(m2)

    println(r);
    println(v1);
    println(v2);
    println(v3);
  }

  def m1(f: Int => Int) = {
    (1 to 10).filter(_ % 2 == 0).map(f).reduce(_ * _);
  }

  def m2: Int => Int = {
    val factor = 2
    val f = (i: Int) => factor * i
    f
  }
}
