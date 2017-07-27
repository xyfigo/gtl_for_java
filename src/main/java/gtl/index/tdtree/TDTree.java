package gtl.index.tdtree;

import gtl.common.BitSet;
import gtl.geom.Envelope;
import gtl.geom.Interval;
import gtl.geom.Intervals;
import gtl.index.shape.IsoscelesRightTriangleShape;

import java.util.ArrayList;

/**
 * Created by ZhenwenHe on 2017/7/17.
 * according to reference
 * Stantic, B., R. Topor, J. Terry and A. Sattar (2010). "Advanced Indexing Technique for Temporal Data." Computer Science and Information Systems 7(4): 679-703.
 */
public class TDTree {
    class TreeNode{
        BitSet identifier;
        Intervals intervals;
        int direction;
    }
    protected IsoscelesRightTriangleShape baseShape;

    int capacity;

    ArrayList<TreeNode> nodes;

    public TDTree(IsoscelesRightTriangleShape baseShape, int capacity) {
        this.baseShape = baseShape;
        this.capacity = capacity;
        this.nodes = new ArrayList<TreeNode>();
    }
    public Intervals query(Envelope e){
        return null;
    }

    public boolean insert(Interval i){
        return false;
    }

    public void split(Interval i, TreeNode curNode){

    }

    public TreeNode find(Interval i){
        return null;
    }
}
