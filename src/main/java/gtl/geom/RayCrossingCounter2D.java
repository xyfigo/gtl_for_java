package gtl.geom;

/**
 * Created by hadoop on 17-3-20.
 */

/**
 * Counts the number of segments crossed by a horizontal ray extending to the right
 * from a given point, in an incremental fashion.
 * This can be used to determine whether a point lies in a {@link Polygonal} geometry.
 * The class determines the situation where the point lies exactly on a segment.
 * When being used for PointShape-In-Polygon determination, this case allows short-circuiting
 * the evaluation.
 * <p>
 * This class handles polygonal geometries with any number of shells and holes.
 * The orientation of the shell and hole rings is unimportant.
 * In order to compute a correct location for a given polygonal geometry,
 * it is essential that <b>all</b> segments are counted which
 * <ul>
 * <li>touch the ray
 * <li>lie in in any ring which may contain the point
 * </ul>
 * The only exception is when the point-on-segment situation is detected, in which
 * case no further processing is required.
 * The implication of the above rule is that segments
 * which can be a priori determined to <i>not</i> touch the ray
 * (i.e. by a test of their bounding box or Y-extent)
 * do not need to be counted.  This allows for optimization by indexing.
 *
 * @author Martin Davis
 */
class RayCrossingCounter2D {
    private Vertex2D p;
    private int crossingCount = 0;
    // true if the test point lies on an input segment
    private boolean isPointOnSegment = false;
    public RayCrossingCounter2D(Vertex2D p) {
        this.p = p;
    }

    /**
     * Determines the {@link Location} of a point in a ring.
     * This method is an exemplar of how to use this class.
     *
     * @param p    the point to test
     * @param ring an array of Coordinates forming a ring
     * @return the location of the point in the ring
     */
    public static int locatePointInRing(Vertex2D p, Vertex2D[] ring) {
        RayCrossingCounter2D counter = new RayCrossingCounter2D(p);

        for (int i = 1; i < ring.length; i++) {
            Vertex2D p1 = ring[i];
            Vertex2D p2 = ring[i - 1];
            counter.countSegment(p1, p2);
            if (counter.isOnSegment())
                return counter.getLocation();
        }
        return counter.getLocation();
    }

    /**
     * Determines the {@link Location} of a point in a ring.
     *
     * @param p    the point to test
     * @param ring a coordinate sequence forming a ring
     * @return the location of the point in the ring
     */
    public static int locatePointInRing(Vertex2D p, VertexSequence ring) {
        RayCrossingCounter2D counter = new RayCrossingCounter2D(p);

        Vertex2D p1 = Geom2DSuits.createVertex2D(0.0, 0.0);
        Vertex2D p2 = Geom2DSuits.createVertex2D(0.0, 0.0);
        for (int i = 1; i < ring.size(); i++) {
            ring.getCoordinate(i, p1);
            ring.getCoordinate(i - 1, p2);
            counter.countSegment(p1, p2);
            if (counter.isOnSegment())
                return counter.getLocation();
        }
        return counter.getLocation();
    }

    /**
     * Counts a segment
     *
     * @param p1 an endpoint of the segment
     * @param p2 another endpoint of the segment
     */
    public void countSegment(Vertex2D p1, Vertex2D p2) {
        /**
         * For each segment, check if it crosses
         * a horizontal ray running from the test point in the positive x direction.
         */

        // check if the segment is strictly to the left of the test point
        if (p1.x < p.x && p2.x < p.x)
            return;

        // check if the point is equal to the current ring vertex
        if (p.x == p2.x && p.y == p2.y) {
            isPointOnSegment = true;
            return;
        }
        /**
         * For horizontal segments, check if the point is on the segment.
         * Otherwise, horizontal segments are not counted.
         */
        if (p1.y == p.y && p2.y == p.y) {
            double minx = p1.x;
            double maxx = p2.x;
            if (minx > maxx) {
                minx = p2.x;
                maxx = p1.x;
            }
            if (p.x >= minx && p.x <= maxx) {
                isPointOnSegment = true;
            }
            return;
        }
        /**
         * Evaluate all non-horizontal segments which cross a horizontal ray to the
         * right of the test pt. To avoid double-counting shared vertices, we use the
         * convention that
         * <ul>
         * <li>an upward edge includes its starting endpoint, and excludes its
         * final endpoint
         * <li>a downward edge excludes its starting endpoint, and includes its
         * final endpoint
         * </ul>
         */
        if (((p1.y > p.y) && (p2.y <= p.y))
                || ((p2.y > p.y) && (p1.y <= p.y))) {
            // translate the segment so that the test point lies on the origin
            double x1 = p1.x - p.x;
            double y1 = p1.y - p.y;
            double x2 = p2.x - p.x;
            double y2 = p2.y - p.y;

            /**
             * The translated segment straddles the x-axis. Compute the sign of the
             * ordinate of intersection with the x-axis. (y2 != y1, so denominator
             * will never be 0.0)
             */
            // double xIntSign = RobustDeterminant2D.signOfDet2x2(x1, y1, x2, y2) / (y2
            // - y1);
            // MD - faster & more robust computation?
            double xIntSign = RobustDeterminant2D.signOfDet2x2(x1, y1, x2, y2);
            if (xIntSign == 0.0) {
                isPointOnSegment = true;
                return;
            }
            if (y2 < y1)
                xIntSign = -xIntSign;
            // xsave = xInt;

            //System.out.println("xIntSign(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + " = " + xIntSign);
            // The segment crosses the ray if the sign is strictly positive.
            if (xIntSign > 0.0) {
                crossingCount++;
            }
        }
    }

    /**
     * Reports whether the point lies exactly on one of the supplied segments.
     * This method may be called at any time as segments are processed.
     * If the result of this method is <tt>true</tt>,
     * no further segments need be supplied, since the result
     * will never change again.
     *
     * @return true if the point lies exactly on a segment
     */
    public boolean isOnSegment() {
        return isPointOnSegment;
    }

    /**
     * Gets the {@link Location} of the point relative to
     * the ring, polygon
     * or multipolygon from which the processed segments were provided.
     * <p>
     * This method only determines the correct location
     * if <b>all</b> relevant segments must have been processed.
     *
     * @return the Location of the point
     */
    public int getLocation() {
        if (isPointOnSegment)
            return Location.BOUNDARY;

        // The point is in the interior of the ring if the number of X-crossings is
        // odd.
        if ((crossingCount % 2) == 1) {
            return Location.INTERIOR;
        }
        return Location.EXTERIOR;
    }

    /**
     * Tests whether the point lies in or on
     * the ring, polygon
     * or multipolygon from which the processed segments were provided.
     * <p>
     * This method only determines the correct location
     * if <b>all</b> relevant segments must have been processed.
     *
     * @return true if the point lies in or on the supplied polygon
     */
    public boolean isPointInPolygon() {
        return getLocation() != Location.EXTERIOR;
    }
}
