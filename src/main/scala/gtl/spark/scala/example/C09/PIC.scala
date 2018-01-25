import org.apache.spark.mllib.clustering.PowerIterationClustering
import org.apache.spark.{SparkConf, SparkContext}


/**
  * https://github.com/xubo245/SparkLearning/blob/master/docs/Spark/MLlibLearning/5%E8%81%9A%E7%B1%BB/Spark%E4%B8%AD%E7%BB%84%E4%BB%B6Mllib%E7%9A%84%E5%AD%A6%E4%B9%A046%E4%B9%8BPower%20iteration%20clustering.md
  * */
object PIC {
  def main(args: Array[String]) {

    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("PIC") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // Load and parse the data
    val data = sc.textFile("D:\\devs\\data\\spark\\D09\\pic.txt")
    val similarities = data.map { line =>
      val parts = line.split(' ')
      (parts(0).toLong, parts(1).toLong, parts(2).toDouble)
    }

    // Cluster the data into two classes using PowerIterationClustering
    val pic = new PowerIterationClustering()
      .setK(2)
      .setMaxIterations(10)
    val model = pic.run(similarities)
    model.assignments.foreach { a =>
      println(s"${a.id} -> ${a.cluster}")
    }
    //    model.pre
    // Save and load model
    //    model.save(sc, "myModelPath")
    //    val sameModel = PowerIterationClusteringModel.load(sc, "myModelPath")F
  }
}
