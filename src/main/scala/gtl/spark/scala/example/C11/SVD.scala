import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.{SparkConf, SparkContext}

object SVD {
  def main(args: Array[String]) {
    //https://gaohueric.github.io/2016/08/20/IDEA%E4%B8%8BLog4j-%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B/
    //http://www.cnblogs.com/chiangchou/p/idea-debug.html
    //设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("SVD ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val data = sc.textFile("D:\\devs\\data\\spark\\D11\\a.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转成Vector格式
    val rm = new RowMatrix(data) //读入行矩阵
    val SVD = rm.computeSVD(2, computeU = true) //进行SVD计算
    val U = SVD.U
    val S = SVD.s
    val V = SVD.V
    println(SVD) //打印SVD结果矩阵
    println(U)
    println(S)
    println(V)

  }
}

/**
  * SingularValueDecomposition(org.apache.spark.mllib.linalg.distributed.RowMatrix@4978777f,[20.149109416118147,6.235013304723683],-0.2534439684458276  0.959479009960656
  * -0.5689753266567847  -0.045293722287124194
  * -0.5539694957328472  -0.1889238879053366
  * -0.5524047703556971  -0.20409868383953073   )
  *org.apache.spark.mllib.linalg.distributed.RowMatrix@4978777f
  * [20.149109416118147,6.235013304723683]
  * -0.2534439684458276  0.959479009960656
  * -0.5689753266567847  -0.045293722287124194
  * -0.5539694957328472  -0.1889238879053366
  * -0.5524047703556971  -0.20409868383953073
  *
  **/
