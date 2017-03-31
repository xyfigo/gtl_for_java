package gtl.geom;

import gtl.math.Float128;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ZhenwenHe on 2017/3/30.
 */
public class InfiniteLineTest {
    @Test
    public void clone1() throws Exception {
    }

    @Test
    public void reset() throws Exception {
    }

    @Test
    public void reset1() throws Exception {
    }

    @Test
    public void copyFrom() throws Exception {
    }

    @Test
    public void getStartPoint() throws Exception {
    }

    @Test
    public void getEndPoint() throws Exception {
    }

    @Test
    public void load() throws Exception {
    }

    @Test
    public void store() throws Exception {
    }

    @Test
    public void getByteArraySize() throws Exception {
    }

    @Test
    public void compareTo() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        Vector2D s = new Vector2D(1.0, 1.0);
        Vector2D e = new Vector2D(1.000000000000000000000000002, 1.000000000000000000000000002);
        Vector2D p = new Vector2D(10000000000000000000003.0, 1000000000000000000004.0);
        Float128 fex = new Float128(e.getX());
        Float128 fsx = new Float128(s.getX());
        Float128 f = fex.selfSubtract(fsx);
        double t = e.getX() - s.getX();
        InfiniteLine il = new InfiniteLine(s, e);
        assertEquals(il.test((Vector) p), 0);
    }

}