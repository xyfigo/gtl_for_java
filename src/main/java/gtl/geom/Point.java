package gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

/**
 * Represents a single point.
 * <p>
 * A <code>PointShape</code> is topologically valid if and only if:
 * <ul>
 * <li>the coordinate which defines it (if any) is a valid coordinate
 * (i.e. does not have an <code>NaN</code> X or Y ordinate)
 * </ul>
 *
 * @version 1.7
 */
public class Point extends Geometry implements Puntal {
    private static final long serialVersionUID = 1L;

    /**
     * The <code>Vertex</code> wrapped by this <code>PointShape</code>.
     */
    private VectorSequence coordinates;


    @Override
    public Object clone() {
        return null;
    }
}


