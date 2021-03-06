package gtl.index.titree;

import gtl.geom.*;
import gtl.index.shape.IsoscelesRightTriangleShape;
import gtl.index.shape.LineSegmentShape;
import gtl.index.shape.PointShape;
import gtl.index.shape.RegionShape;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by ZhenwenHe on 2017/3/27.
 */
public class TITree {
    /**
     *树的根节点，其所包含的三角形范围为baseTriangle
     */
    TreeNode rootNode;
    /**
     * 基准三角形，记录根节点的范围，其始终是等腰直角三角形
     * V0为直角顶点，节点按照逆时针方向排列，分别V1，V2；
     * V0V1为Y轴方向，V2V0为X轴方向，作为基准坐标系的时候，V1为原点；
     * 基准三角形的范围可以左扩展（leftExtension），
     * 也可以右扩展（rightExtension），可以无限扩大；
     * 扩展后的基准三角形与原来的基准三角形为为相似三角形
     */
    IsoscelesRightTriangleShape baseTriangle;
    /**
     * 每个叶子节点中最多能存放leafNodeCapacity个间隔数据对象
     */
    int leafNodeCapacity;

    /**
     * 将间隔查询转成点、线、面三种类型的空间查询
     * 如果对基本三角形进行了扩展，生成器中的基本
     * 三角形要跟着变动
     */
    QueryShapeGenerator queryShapeGenerator;

    /**
     *
     * @param baseTriangle
     * @param leafNodeCapacity
     */
    public TITree(IsoscelesRightTriangleShape baseTriangle, int leafNodeCapacity) {
        this.baseTriangle = (IsoscelesRightTriangleShape) baseTriangle.clone();
        this.leafNodeCapacity=leafNodeCapacity;
        this.rootNode=new TreeNode();
        this.rootNode.intervals=new ArrayList<>();
        rootNode.triangle = this.baseTriangle;
        queryShapeGenerator = new QueryShapeGenerator(this.baseTriangle);
    }

    /**
     * 算法描述：
     * 1）如果不在本范围内，则调用extend方法进行三角形范围扩展
     * 1）调用findTreeNode查找i 要插入的节点tn（必定是叶子节点）
     * 2）如果tn的间隔数据对象个数小于leafNodeCapacity，则直接加入该节点
     * 3）如果tn中的间隔数据对象等于leafNodeCapacity，
     *      则执行节点分裂算法splitTreeNode，并将i插入
     *
     * @param i
     * @return
     */
    public boolean insert(Interval i){
        if (test(this.baseTriangle, i) == 0) {
            this.rootNode = extend(i);
            this.baseTriangle = this.rootNode.triangle;
        }

        TreeNode tn = findTreeNode(i);
        if(tn.intervals.size()<leafNodeCapacity){
            tn.intervals.add(i);
        } else {
            return splitTreeNode(i,tn);
        }
        return true;
    }

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     * @param  triangle
     * @param i
     * @return 0- out of triangle
     *          1-left sub triangle
     *          2- right sub triangle
     */
    int test(IsoscelesRightTriangleShape triangle, Interval i) {
        Vector2D v = new Vector2D(i.getLowerBound(), i.getUpperBound());
        return test(triangle, v);
    }

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     *
     * @param triangle
     * @param i
     * @return 0- out of triangle
     * 1-left sub triangle
     * 2- right sub triangle
     */
    int test(IsoscelesRightTriangleShape triangle, PointShape i) {
        Vector2D v = new Vector2D(i.getX(), i.getY());
        return test(triangle, v);
    }

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     *
     * @param triangle
     * @param i
     * @return 0- out of triangle
     * 1-left sub triangle
     * 2- right sub triangle
     */
    int test(IsoscelesRightTriangleShape triangle, Vector v) {
        if (!triangle.contains(v)) {
            return 0;
        }
        Triangle left = triangle.leftTriangle();
        if (left.contains(v))
            return 1;
        else
            return 2;
    }
    /**
     * tn 是一个子节点，其中包含的间隔数据对象个数达到leafNodeCapacity
     * 在该节点中药插入i，则需要进行节点分裂,算法步骤如下：
     * 1)生成一个新的内部节点p,设置p的父节点为tn的父节点
     *
     * @param i
     * @param tn
     * @return
     */
    boolean splitTreeNode(Interval i, TreeNode tn) {

        ArrayList<Interval> intervals = tn.intervals;
        ArrayList<Interval> leftIntervals = new ArrayList<>(0);
        ArrayList<Interval> rightIntervals = new ArrayList<>(0);
        IsoscelesRightTriangleShape leftTriangleShape, rightTriangleShape;
        tn.intervals = null;
        intervals.add(i);
        TreeNode p = tn;
        TreeNode left, right;
        boolean loopFlag = true;

        while (loopFlag) {
            left = new TreeNode();
            left.parent = p;
            p.left = left;
            leftTriangleShape = new IsoscelesRightTriangleShape(p.triangle.leftTriangle());
            left.triangle = leftTriangleShape;

            right = new TreeNode();
            right.parent = p;
            p.right = right;
            rightTriangleShape = new IsoscelesRightTriangleShape(p.triangle.rightTriangle());
            right.triangle = rightTriangleShape;

            for (Interval it : intervals) {
                if (leftTriangleShape.contains(it)) {//在三角形外
                    leftIntervals.add(it);
                } else
                    rightIntervals.add(it);
            }
            //全部插入到左边三角形了，需要分解后重插
            if (leftIntervals.size() == intervals.size()) {
                leftIntervals.clear();
                p = left;
            }
            //全部插入到右边三角形了，需要分解后重插
            else if (rightIntervals.size() == intervals.size()) {
                rightIntervals.clear();
                p = right;
            }
            //分解完毕
            else {
                left.intervals = leftIntervals;
                right.intervals = rightIntervals;
                loopFlag = false;
            }
        }

        return true;
    }

    /**
     * 查找待插入的节点，返回必定为叶子节点，
     * 如果为空则表示应该调用extend函数要进行基准三角形扩展
     * 算法描述：
     * 1）让p指向根节点
     * 2）测试p的三角形范围与间隔数据对象的位置关系
     * 3) 如果i在p的左三角形里面或边上，让p指向其左节点
     * 4）如果i在p的右三角形里面或边上，让p指向其右节点
     * 5）如果p是叶子节点，则返回p;否则跳转到2）
     * @param i
     * @return
     */
    TreeNode findTreeNode(Interval i){
        TreeNode p = rootNode;
        int testResult=0;
        while (p!=null){
            testResult=test(p.triangle,i);
            if(testResult==0)
                return null;
            else if(testResult==1){
                if (p.isLeafNode())
                    return p;
                else
                    p = p.left;
            } else {//=2
                if (p.isLeafNode())
                    return p;
                else
                    p = p.right;
            }
        }
        return null;
    }

    /**
     * 如果i不在baseTriangle里面或边上，则需要扩展baseTriangle
     *
     *
     * @param i
     * @return the new root node
     */
    TreeNode extend(Interval i) {
        TreeNode newRoot = this.rootNode;
        IsoscelesRightTriangleShape newBaseTriangle = this.rootNode.triangle;
        Vector V0;
        while (test(newBaseTriangle, i) == 0) {
            V0 = newRoot.triangle.getVertex(0);
            if (i.getLowerBound() <= V0.getX())
                newRoot = leftExtension(newRoot);
            else
                newRoot = rightExtension(newRoot);
            newBaseTriangle = newRoot.triangle;
        }
        this.rootNode = newRoot;
        this.baseTriangle = newBaseTriangle;
        this.queryShapeGenerator.baseTriangle = this.baseTriangle;
        return newRoot;
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->left extension
     *                                  newRoot
     *                         left              right
     *                                    rootNode     right
     * @param tn
     * @return
     */
    TreeNode leftExtension(TreeNode tn) {
        IsoscelesRightTriangleShape baseT = tn.triangle;

        Vector[] vertices = baseT.getClockwiseVertices();
        Vector V0 = new VectorImpl(vertices[0].getX() - (vertices[2].getX() - vertices[0].getX()),
                vertices[0].getY(), 0.0);
        Vector V2 = vertices[2];
        Vector V1 = new VectorImpl(V0.getX(),
                V0.getY() - 2 * (vertices[0].getY() - vertices[1].getY()), 0.0);
        baseT = new IsoscelesRightTriangleShape(V0, V1, V2);
        TreeNode newRootNode = new TreeNode();
        newRootNode.triangle = baseT;

        newRootNode.left = new TreeNode();
        newRootNode.left.parent = newRootNode;
        newRootNode.left.intervals = new ArrayList<>();
        newRootNode.left.triangle = new IsoscelesRightTriangleShape(baseT.leftTriangle().getVertices());

        newRootNode.right = new TreeNode();
        newRootNode.right.parent = newRootNode;
        newRootNode.right.triangle = new IsoscelesRightTriangleShape(baseT.rightTriangle().getVertices());

        TreeNode p = newRootNode.right;
        p.right = new TreeNode();
        p.right.parent = p;
        p.right.triangle = new IsoscelesRightTriangleShape(
                p.triangle.rightTriangle().getVertices());
        p.right.intervals = new ArrayList<>();

        p.left = tn;
        p.left.parent = p;

        return newRootNode;
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->right extension
     * newRoot
     * left              right(leaf)
     * left（leaf)  rootNode
     *
     * @param tn
     * @return
     */
    TreeNode rightExtension(TreeNode tn){
        IsoscelesRightTriangleShape baseT = tn.triangle;
        Vector[] vertices = baseT.getVertices();
        Vector V0 = new VectorImpl(vertices[0].getX(),
                vertices[0].getY() + (vertices[0].getY() - vertices[1].getY()), 0.0);
        Vector V1 = vertices[1];
        Vector V2 = new VectorImpl(vertices[2].getX() + vertices[2].getX() - vertices[0].getX(),
                V0.getY(), 0.0);
        baseT = new IsoscelesRightTriangleShape(V0, V1, V2);
        TreeNode newRootNode = new TreeNode();
        newRootNode.triangle = baseT;

        newRootNode.right = new TreeNode();
        newRootNode.right.parent = newRootNode;
        newRootNode.right.intervals = new ArrayList<>();
        newRootNode.right.triangle = new IsoscelesRightTriangleShape(baseT.rightTriangle().getVertices());

        newRootNode.left = new TreeNode();
        newRootNode.left.parent = newRootNode;
        newRootNode.left.triangle = new IsoscelesRightTriangleShape(baseT.leftTriangle().getVertices());

        TreeNode p = newRootNode.left;
        p.left = new TreeNode();
        p.left.parent = p;
        p.left.triangle = new IsoscelesRightTriangleShape(
                p.triangle.leftTriangle().getVertices());
        p.left.intervals = new ArrayList<>();

        p.right = tn;
        p.right.parent = p;

        return newRootNode;
    }

    /**
     * 点查询，也即是间隔数据的相等查询
     *
     * @param ps 由QueryShapeGenerator生成的点状查询图形
     * @param f  对于查询结果集合中的每个Interval执行函数f
     * @return 返回查询结果的个数
     */
    int pointQuery(PointShape ps, Function<Interval, Boolean> f) {
        TreeNode p = this.rootNode;
        IsoscelesRightTriangleShape pTri;
        int retVal;
        int s = 0;
        while (p != null) {
            pTri = p.triangle;
            retVal = test(pTri, ps);
            if (retVal == 0) {//out
                return s;
            } else {//in
                if (p.isLeafNode()) {
                    for (Interval v : p.intervals) {
                        if (v.getLowerBound() == ps.getX()
                                && v.getUpperBound() == ps.getY()) {
                            f.apply(v);
                            s++;
                        }
                    }
                    return s;
                } else {
                    if (retVal == 1) {//left
                        p = p.left;
                    } else {//right
                        p = p.right;
                    }
                }
            }
        }
        return s;
    }

    /**
     * 线查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param lsp 由QueryShapeGenerator生成的线状查询图形
     * @param f   对于查询结果集合中的每个Interval执行函数f
     * @return 返回查询结果的个数
     */
    int lineQuery(LineSegmentShape lsp, Function<Interval, Boolean> f) {
        Vector s = lsp.getStartPoint();
        Vector e = lsp.getEndPoint();
        TreeNode p = rootNode;
        int c = 0;
        int testS, testE;

        testE = test(p.triangle, lsp.getEndPoint());
        testS = test(p.triangle, lsp.getStartPoint());

        //如果线段的两个点都不在三角形内，则直接返回
        //这里还有一种可能的情况是，线段穿越了三角形，
        //并且两个端点都在三角形的外面，但是由于该函数
        //传入的线段都是QueryShapeGenerator生成的,不可
        //能出现这种情况，所以，这里可以不考虑。
        if (testE == 0 && testS == 0) return c;

        if (p.isLeafNode()) {
            for (Interval tv : p.intervals) {
                //test point on segment
                f.apply(tv);
                c++;
            }
            return c;
        } else {
            //两个点都在左边三角形
            if (testE == 1 && testS == 1) {
                p = p.left;
            }
            //两个点都在右边三角形
            else if (testE == 2 && testS == 2) {
                p = p.right;
            }
            //一个点在左边三角形，一个在右边三角形
            else {
                return c;
            }
        }
        return c;
    }

    /**
     * 区域查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param rs 由QueryShapeGenerator生成的三角形（由于e>s，
     *           所以三角形扩展成矩形也不会影响查询结果，
     *           这样更加便于相交计算）或矩形查询图形
     * @param f  对于查询结果集合中的每个Interval执行函数f
     * @return 返回查询结果的个数
     */
    int regionQuery(RegionShape rs, Function<Interval, Boolean> f) {
        return 0;
    }

    /**
     * 树节点类，如果intervals==null，则为内部节点，
     * 否则为外部节点或叶子节点；
     * 当为内部节点的时候，left指向左子树节点，
     * right指向右子树节点；
     * parent指向父节点，如果父节点为空，则为根节点；
     * triangle是节点覆盖的三角形范围。
     */
    class TreeNode {
        IsoscelesRightTriangleShape triangle;
        TreeNode parent;
        TreeNode left;
        TreeNode right;
        /**
         * if null, internal node
         * else external node , or leaf
         */
        ArrayList<Interval> intervals;

        public TreeNode() {
            this.triangle = null;
            this.parent = null;
            this.left = null;
            this.right = null;
            this.intervals = null;
        }

        boolean isLeafNode() {
            return intervals != null;
        }

    }

}
