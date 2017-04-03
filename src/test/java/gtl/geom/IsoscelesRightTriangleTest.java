package gtl.geom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ZhenwenHe on 2017/4/3.
 */
public class IsoscelesRightTriangleTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void clone1() throws Exception {
    }

    @Test
    public void contains() throws Exception {
        IsoscelesRightTriangle root = new IsoscelesRightTriangle(
                new Vector2D(0, 1),
                new Vector2D(0, 0),
                new Vector2D(1, 1));
        IsoscelesRightTriangle left = new IsoscelesRightTriangle(root.leftTriangle().getVertices());
        IsoscelesRightTriangle right = new IsoscelesRightTriangle(root.rightTriangle().getVertices());

        Vector2D p = new Vector2D(0.7406624718675487, 0.977297686735782);
        Vector2D p1 = new Vector2D(0.6293026188616665, 0.6852137913274065);
        Vector2D p2 = new Vector2D(0, 0.5);
        Vector2D p3 = new Vector2D(0.5, 1.0);
        Vector2D p4 = new Vector2D(0.5, 0.5);//u=0.5,v=0.5
        Vector2D p41 = new Vector2D(0.8, 0.8);
        Vector2D p42 = new Vector2D(0.3, 0.3);
        Vector2D p5 = new Vector2D(0.0, 1.0);
        Vector2D p51 = new Vector2D(0.0, 0.0);
        Vector2D p52 = new Vector2D(1.0, 1.0);

        assertTrue(root.contains(p2));
        assertTrue(root.contains(p3));
        assertTrue(root.contains(p4));
        assertTrue(root.contains(p41));
        assertTrue(root.contains(p42));
        assertTrue(root.contains(p5));
        assertTrue(root.contains(p51));
        assertTrue(root.contains(p52));
        assertTrue(right.contains(p1));
        assertTrue(left.contains(p) == false);
    }
}