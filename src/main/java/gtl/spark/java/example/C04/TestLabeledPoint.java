package gtl.spark.java.example.C04;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

/**
 * 标注点LabeledPoint是一种带有标签（Label/Response）的本地向量，它可以是稠密或者是稀疏的。
 * 在MLlib中，标注点在监督学习算法中被使用。由于标签是用双精度浮点型来存储的，故标注点类型在回归（Regression）和分类（Classification）问题上均可使用。
 * 例如，对于二分类问题，则正样本的标签为1，负样本的标签为0，而对于多类别的分类问题来说，标签则应是一个以0开始的索引序列:0, 1, 2 ...
 * <p>
 * 标注点的实现类是org.apache.spark.mllib.regression.LabeledPoint，请注意它与前面介绍的本地向量不同，并不位于linalg包下
 *
 * 在实际的机器学习问题中，稀疏向量数据是非常常见的，MLlib提供了读取LIBSVM格式数据的支持，该格式被广泛用于LIBSVM、LIBLINEAR等机器学习库。
 * 在该格式下，每一个带标注的样本点由以下格式表示：
 * label index1:value1 index2:value2 index3:value3 ...
 * 其中label是该样本点的标签值，一系列index:value对则代表了该样本向量中所有非零元素的索引和元素值。这里需要特别注意的是，index是以1开始并递增的。
 * MLlib在org.apache.spark.mllib.util.MLUtils工具类中提供了读取LIBSVM格式的方法loadLibSVMFile，其使用非常方便。
 */
public class TestLabeledPoint {
    public static void main(String[] args) {
        // Create a labeled point with a positive label and a dense feature vector.
        LabeledPoint pos = new LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0));

// Create a labeled point with a negative label and a sparse feature vector.
        LabeledPoint neg = new LabeledPoint(0.0, Vectors.sparse(3, new int[]{0, 2}, new double[]{1.0, 3.0}));

        System.out.println(pos);
        System.out.println(neg);

        //MLUtils.loadLibSVMFile
        SparkConf conf = new SparkConf().setAppName("TestLabeledPoint").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<LabeledPoint> examples =
                MLUtils.loadLibSVMFile(jsc.sc(), "D:\\devs\\3rdparties\\spark\\spark-2.2.1\\data\\mllib\\sample_libsvm_data.txt").toJavaRDD();
        System.out.println(examples.collect().size());  //100
    }
}
