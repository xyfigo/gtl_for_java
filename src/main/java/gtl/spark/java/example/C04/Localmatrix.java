package gtl.spark.java.example.C04;

import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;


/**
 * 本地矩阵具有整型的行、列索引值和双精度浮点型的元素值，它存储在单机上。MLlib支持稠密矩阵DenseMatrix和稀疏矩阵Sparse Matrix两种本地矩阵，稠密矩阵将所有元素的值存储在一个列优先（Column-major）的双精度型数组中，而稀疏矩阵则将非零元素以列优先的CSC（Compressed Sparse Column）模式进行存储，关于CSC等稀疏矩阵存储方式的具体实现，可以参看Sparse Matrix Compression Formats一文。
 * <p>
 * 本地矩阵的基类是org.apache.spark.mllib.linalg.Matrix，DenseMatrix和SparseMatrix均是它的实现类，和本地向量类似，MLlib也为本地矩阵提供了相应的工具类Matrices，调用工厂方法即可创建实例
 */

public class Localmatrix {
    public static void main(String[] args) {

// 创建一个3行2列的稠密矩阵[ [1.0,2.0], [3.0,4.0], [5.0,6.0] ]
// 请注意，这里的数组参数是列先序的！

        Matrix dm = Matrices.dense(3, 2, new double[]{1.0, 3.0, 5.0, 2.0, 4.0, 6.0});

// 创建一个3行2列的稀疏矩阵[ [9.0,0.0], [0.0,8.0], [0.0,6.0]]
// 第一个数组参数表示列指针，即每一列元素的开始索引值
// 第二个数组参数表示行索引，即对应的元素是属于哪一行
// 第三个数组即是按列先序排列的所有非零元素，通过列指针和行索引即可判断每个元素所在的位置
        Matrix sm = Matrices.sparse(3, 2, new int[]{0, 1, 3}, new int[]{0, 2, 1}, new double[]{9, 6, 8});
    }
}
