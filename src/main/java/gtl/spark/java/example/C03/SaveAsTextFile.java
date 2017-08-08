package gtl.spark.java.example.C03;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://www.jianshu.com/p/4d4a7fdaca5c
 * http://lxw1234.com/archives/2015/07/402.htm
 *
 * saveAsTextFile用于将RDD以文本文件的格式存储到文件系统中。
 * 从源码中可以看到，saveAsTextFile函数是依赖于saveAsHadoopFile函数，
 * 由于saveAsHadoopFile函数接受PairRDD，所以在saveAsTextFile函数中利用rddToPairRDDFunctions函数转化为(NullWritable,Text)类型的RDD，
 * 然后通过saveAsHadoopFile函数实现相应的写操作
 *
 *
 *
 * 从源码中可以看出，saveAsObjectFile函数是依赖于saveAsSequenceFile函数实现的，
 * 将RDD转化为类型为<NullWritable,BytesWritable>的PairRDD，
 * 然后通过saveAsSequenceFile函数实现。
 * 在spark的java版的api中没有实现saveAsSequenceFile函数，该函数类似于saveAsTextFile函数。
 * */

public class SaveAsTextFile {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("SaveAsTextFile").setMaster("local");
        JavaSparkContext ctx = new JavaSparkContext(conf);

        List<Tuple2<String, String>> listR = new ArrayList<Tuple2<String, String>>();
        listR.add(new Tuple2<String, String>("a1", "a2"));
        listR.add(new Tuple2<String, String>("b1", "b2"));
        listR.add(new Tuple2<String, String>("c1", "c2"));

        List<Tuple2<String, String>> listS = new ArrayList<Tuple2<String, String>>();
        listS.add(new Tuple2<String, String>("d1", "d2"));
        listS.add(new Tuple2<String, String>("e1", "e2"));
        listS.add(new Tuple2<String, String>("f1", "f2"));
        listS.add(new Tuple2<String, String>("g1", "g2"));

        // create two RDD's
        JavaPairRDD<String, String> R = ctx.parallelizePairs(listR);
        JavaPairRDD<String, String> S = ctx.parallelizePairs(listS);

        // <U> JavaPairRDD<T,U> cartesian(JavaRDDLike<U,?> other)
        // Return the Cartesian product of this RDD and another one,
        // that is, the RDD of all pairs of elements (a, b)
        // where a is in this and b is in other.
        JavaPairRDD<Tuple2<String, String>, Tuple2<String, String>> cart = R.cartesian(S);

        // save the result
        cart.saveAsTextFile("D:\\temp\\model\\saveAsTextFileResult3");
        cart.saveAsObjectFile("D:\\temp\\model\\saveAsObjectFileResult3");

        JavaRDD<String> result=ctx.textFile("D:\\temp\\model\\Result3");

//        for (Iterator iter = result.collect().iterator(); iter.hasNext();) {
//            System.out.println(iter.next().toString());
//        }

        result.foreach(s -> System.out.println(s));

        // done
        ctx.close();
        System.exit(0);
    }
}
