package gtl.spark.java.example.C04;

import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;

/**
 * https://spark.apache.org/docs/latest/mllib-data-types.html
 * http://dblab.xmu.edu.cn/blog/1172/
 *
 * <p>
 * MLLib提供了一序列基本数据类型以支持底层的机器学习算法。
 * 主要的数据类型包括：本地向量、标注点（Labeled Point）、本地矩阵、分布式矩阵等。
 * 单机模式存储的本地向量与矩阵，以及基于一个或多个RDD的分布式矩阵。
 * 其中本地向量与本地矩阵作为公共接口提供简单数据模型，底层的线性代数操作由Breeze库和jblas库提供。
 * 标注点类型用来表示监督学习（Supervised Learning）中的一个训练样本。
 * <p>
 * 本地向量存储在单机上，其拥有整型、从0开始的索引值以及浮点型的元素值。
 * MLlib提供了两种类型的本地向量，稠密向量DenseVector和稀疏向量SparseVector。
 * 稠密向量使用一个双精度浮点型数组来表示其中每一维元素，而稀疏向量则是基于一个整型索引数组和一个双精度浮点型的值数组。
 * 例如，向量(1.0, 0.0, 3.0)的稠密向量表示形式是[1.0,0.0,3.0]，而稀疏向量形式则是(3, [0,2], [1.0, 3.0])，其中，3是向量的长度，[0,2]是向量中非0维度的索引值，表示位置为0、2的两个元素为非零值，而[1.0, 3.0]则是按索引排列的数组元素值。
 * <p>
 * 所有本地向量都以org.apache.spark.mllib.linalg.Vector为基类，DenseVector和SparseVector分别是它的两个实现类，故推荐使用Vectors工具类下定义的工厂方法来创建本地向量
 */
public class TestVector {
    public static void main(String[] args) {
        // Create a dense vector (1.0, 0.0, 3.0).
        Vector dv = Vectors.dense(1.0, 0.0, 0.0);
        // Create a sparse vector (1.0, 0.0, 3.0) by specifying its indices and values corresponding to nonzero entries.
        Vector sv = Vectors.sparse(3, new int[]{0, 2}, new double[]{1.0, 3.0});
        System.out.println(dv);
        System.out.println(sv);
    }
}
