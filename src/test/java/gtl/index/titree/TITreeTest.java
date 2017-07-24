package gtl.index.titree;

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
public class TITreeTest {
    Interval[] intervalArray;
    TITree tree;

    public TITreeTest() {
        System.out.println("TITreeTest()");
    }
    @Before
    public void setUp() throws Exception {
        System.out.println("TITreeTest.setUp()");


    }

    @After
    public void tearDown() throws Exception {
        System.out.println("TITreeTest.tearDown()");
    }

    @Test
    public void insert() throws Exception {
        intervalArray = GeomSuits.generateRandomIntervals(100000);
        tree = new TITree(new IsoscelesRightTriangleShape(
                new Vector2D(0, 1),
                new Vector2D(0, 0),
                new Vector2D(1, 1)),
                128);
        System.out.println("TITreeTest.insert()");
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