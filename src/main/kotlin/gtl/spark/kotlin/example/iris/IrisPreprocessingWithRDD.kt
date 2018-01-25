package gtl.spark.kotlin.example.iris

import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.SparkSession
import scala.Tuple5
import java.util.regex.Pattern

object IrisPreprocessingWithRDD {
    private val COMMA_SPLITTER = Pattern.compile(",")
    private val NUMERIC = Pattern.compile("-?[0-9]+\\.?[0-9]*")
    var DATA_FILE = "d:\\devs\\data\\spark\\iris.txt"

    fun calculateMeanAndVariance(
            tuples: JavaRDD<Tuple5<Int, Double, Double, Double, Double>>,
            species: Int, attributeID: Int) {

        val setosaSepalLengths = tuples
                .filter { t -> t._1() === species }
                .map { t -> t._2() }
        //setosaSepalLengths.foreach(s->System.out.println(s));
        val dv = setosaSepalLengths.map { d -> Vectors.dense(d) }
        val s = Statistics.colStats(dv.rdd())
        println("setosa's sepal length mean:" + s.mean())
        println("setosa's sepal length variance:" + s.variance())
        println("setosa's sepal length normL1:" + s.normL1())
        println("setosa's sepal length normL2:" + s.normL2())
        println(attributeID);
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val spark = SparkSession
                .builder()
                .master("local")
                .appName("IrisPreprocessingWithRDD")
                .orCreate
        val sc = JavaSparkContext.fromSparkContext(spark.sparkContext())
        val lines = sc.textFile(DATA_FILE)
        //lines.foreach(s->System.out.println(s));


        //对每行中的字符串以空格分割，形成新集合
        //     species    sepalLength  sepalWidth petalLength petalWidth
        val tuples = lines.map { s ->
            val ss = COMMA_SPLITTER.split(s)
            var species: Int
            if (ss[4].compareTo("Iris-virginica") == 0)
            //1
                species = 3
            else if (ss[4].compareTo("Iris-versicolor") == 0)
            //2
                species = 2
            else if (ss[4].compareTo("Iris-setosa") == 0)
            //3
                species = 1
            else
                species = 0
            Tuple5<Int, Double, Double, Double, Double>(
                    species, java.lang.Double.valueOf(ss[0]), java.lang.Double.valueOf(ss[1]), java.lang.Double.valueOf(ss[2]), java.lang.Double.valueOf(ss[3]))
        }
        tuples.foreach { s -> println(s) }

        //计算Setosa的sepalLength的均值和方差
        val setosaSepalLengths = tuples
                .filter { t -> t._1() == 1 }
                .map { t -> t._2() }
        //setosaSepalLengths.foreach(s->System.out.println(s));
        var dv = setosaSepalLengths.map { d -> Vectors.dense(d) }
        var s = Statistics.colStats(dv.rdd())
        println("setosa's sepal length mean:" + s.mean())
        println("setosa's sepal length variance:" + s.variance())
        println("setosa's sepal length normL1:" + s.normL1())
        println("setosa's sepal length normL2:" + s.normL2())
        //计算Versicolor的sepalLength的均值和方差
        dv = tuples.filter { t -> t._1() == 2 }.map { t -> t._2() }.map { d -> Vectors.dense(d) }
        s = Statistics.colStats(dv.rdd())
        println("versicolor's sepal length mean:" + s.mean())
        println("versicolor's sepal length variance:" + s.variance())
        println("versicolor's sepal length normL1:" + s.normL1())
        println("versicolor's sepal length normL2:" + s.normL2())

        //计算virginica的sepalLength的均值和方差
        dv = tuples.filter { t -> t._1() == 3 }.map { t -> t._2() }.map { d -> Vectors.dense(d) }
        s = Statistics.colStats(dv.rdd())
        println("virginica's sepal length mean:" + s.mean())
        println("virginica's sepal length variance:" + s.variance())
        println("virginica's sepal length normL1:" + s.normL1())
        println("virginica's sepal length normL2:" + s.normL2())
        //计算setosa, versicolor, virginica的sepalLength的均值和方差
        dv = tuples.filter { t -> t._1() >= 1 && t._1() <= 3 }.map { t -> t._2() }.map { d -> Vectors.dense(d) }
        s = Statistics.colStats(dv.rdd())
        println("all sepal length mean:" + s.mean())
        println("all sepal length variance:" + s.variance())
        println("all sepal length normL1:" + s.normL1())
        println("all sepal length normL2:" + s.normL2())
    }
}
