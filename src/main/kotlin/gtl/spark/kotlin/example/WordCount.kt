import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import scala.Tuple2
import java.util.*
import java.util.regex.Pattern

object WordCount {
    @JvmStatic
    fun main(args: Array<String>) {
        val sparkConf = SparkConf().setMaster("local").setAppName("Aggredate")
        val sc = JavaSparkContext(sparkConf)
        /*wc.txt
        good bad cool
        hadoop spark mllib
        good spark mllib
        cool spark bad
         */
        val rdd = sc.textFile("d:\\devs\\data\\spark\\wc.txt")
        //对每行中的字符串以空格分割，形成新集合，也就是说通过flatMap变换后
        //RDD由四行变成12行,也就是12个单词
        val words = rdd.flatMap { s -> Arrays.asList(*Pattern.compile(" ").split(s)).iterator() }

        //def mapToPair[K2, V2](f :  PairFunction[T, K2, V2]) :  JavaPairRDD[K2, V2]
        // public interface PairFunction<T, K, V> extends Serializable {
        //    Tuple2<K, V> call(T var1) throws Exception;
        //}
        //将这12个单词变成12个Tuple2<String,Integer>对象
        val ones = words.mapToPair { s -> Tuple2(s, 1) }
        //def reduceByKey(func : Function2[V, V, V]) : JavaPairRDD[K, V]
        val counts = ones.reduceByKey { i1, i2 -> i1!! + i2!! }
        val output = counts.collect()
        for (tuple in output) {
            println(tuple._1() + ": " + tuple._2())
        }
    }
}
