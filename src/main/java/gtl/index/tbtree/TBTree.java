package gtl.index.tbtree;

import gtl.common.Identifier;
import gtl.geom.*;
import gtl.index.shape.IsoscelesRightTriangleShape;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreeNode;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by ZhenwenHe on 2017/7/24.
 * according to reference
 * He, Z., M.-J. Kraak, O. Huisman, X. Ma and J. Xiao (2013). "Parallel indexing technique for spatio-temporal data." Isprs Journal of Photogrammetry and Remote Sensing 78(0): 116-128.
 * Triangle Binary Tree
 */
public class TBTree {
    class TreeNode{
        TreeNode left;
        TreeNode right;
        IsoscelesRightTriangleShape shape;
        Intervals intervals;//if intervals is null, this node is a internal node
        public boolean isLeaf(){
            if(left==null && right==null)
                return true;
            else
                return false;
        }
    }

    protected TreeNode rootNode;
    protected int leafNodeCapacity;

    public TBTree(IsoscelesRightTriangleShape baseShape,int leafNodeCapacity){
        rootNode = new TreeNode();
        rootNode.left=null;
        rootNode.right=null;
        rootNode.shape=new IsoscelesRightTriangleShape(baseShape);
        rootNode.intervals= GeomSuits.createIntervals();
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
        return -1;
    }
    public TreeNode find(Interval i,TreeNode curNode) {
        int t = test(curNode.shape, i);
        if (t == 1) {
            if (curNode.left == null)
                return curNode;
            else
                return find(i, curNode.left);
        } else if (t == 2) {
            if (curNode.right == null)
                return curNode;
        } else {
            return null;
        }
        return curNode;
    }

    /**
     * divided this node into two nodes: left and right
     */
    public void split(Interval interval,TreeNode curNode){
        assert curNode.isLeaf()==true;
        TreeNode left = new TreeNode();
        left.left=null;
        left.right=null;
        left.shape = new IsoscelesRightTriangleShape(curNode.shape.leftTriangle());
        left.intervals=GeomSuits.createIntervals();

        TreeNode right = new TreeNode();
        right.left=null;
        right.right=null;
        right.shape = new IsoscelesRightTriangleShape(curNode.shape.rightTriangle());
        right.intervals=GeomSuits.createIntervals();

        Intervals intervals=curNode.intervals;
        for (Interval i: intervals){
            if(left.shape.contains(i)){
                left.intervals.add(i);
            }
            else {
                right.intervals.add(i);
            }
        }

        curNode.left=left;
        curNode.right=right;
        curNode.intervals=null;


        if(left.shape.contains(interval)){
            left.intervals.add(interval);
        }
        else {
            right.intervals.add(interval);
        }

        if(left.intervals.size()==leafNodeCapacity) {
            curNode=left;
            split(interval,left);
        }
        if(right.intervals.size()==leafNodeCapacity) {
            curNode=right;
            split(interval,curNode);
        }

        if(curNode.left.shape.contains(interval)){
            curNode.left.intervals.add(interval);
        }
        else {
            curNode.right.intervals.add(interval);
        }
    }

    public boolean insert(Interval i){
        TreeNode curNode = find(i,rootNode);
        if (curNode==null)
            return false;
        else{
            if(curNode.intervals.size()<leafNodeCapacity){
                curNode.intervals.add(i);
            }
            else {
                split(i,curNode);
            }
            return true;
        }
    }

    public void query(TreeNode curNode, Envelope e, Intervals intervals){
        if(curNode==null) return;
        if(curNode.shape.intersects(e)){
            if(curNode.isLeaf()){
                for(Interval i : curNode.intervals){
                    if(e.contains(new Vector2D(i.getLowerBound(),i.getUpperBound()))){
                        intervals.add(i);
                    }
                }
            }
            else{
                query(curNode.left,e,intervals);
                query(curNode.right,e,intervals);
            }
        }
        else
            return;
    }

    public Intervals query(Envelope e){
        Intervals intervals = GeomSuits.createIntervals();
        query(rootNode,e,intervals);
        return intervals;
    }
}
