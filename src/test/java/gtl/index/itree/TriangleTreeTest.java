package gtl.index.itree;

import gtl.geom.GeomSuits;
import gtl.geom.Interval;
import gtl.geom.Vector2D;
import gtl.index.shape.IsoscelesRightTriangleShape;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ZhenwenHe on 2017/3/29.
 */
public class TriangleTreeTest {
    Interval[] intervalArray;
    TriangleTree tree;

    public TriangleTreeTest() {
        System.out.println("TriangleTreeTest()");
    }
    @Before
    public void setUp() throws Exception {
        System.out.println("TriangleTreeTest.setUp()");


    }

    @After
    public void tearDown() throws Exception {
        System.out.println("TriangleTreeTest.tearDown()");
    }

    @Test
    public void insert() throws Exception {
        intervalArray = GeomSuits.generateRandomIntervals(100000);
        tree = new TriangleTree(new IsoscelesRightTriangleShape(
                new Vector2D(0, 1),
                new Vector2D(0, 0),
                new Vector2D(1, 1)),
                128);
        System.out.println("TriangleTreeTest.insert()");
        int k = 0;
        for (Interval i : intervalArray) {
            tree.insert(i);
            k++;
        }
    }

    @Test
    public void test1() throws Exception {
    }

    @Test
    public void splitTreeNode() throws Exception {
    }

    @Test
    public void findTreeNode() throws Exception {
    }

    @Test
    public void extend() throws Exception {
    }

    @Test
    public void leftExtension() throws Exception {
    }

    @Test
    public void rightExtension() throws Exception {
    }

}