package gtl.index.itree;

import gtl.geom.Interval;
import gtl.geom.Vector2D;
import gtl.index.shape.*;

/**
 * Created by ZhenwenHe on 2017/4/3.
 */
class QueryShapeGenerator {
    //refer to the tree's base triangle
    IsoscelesRightTriangleShape baseTriangle;

    public QueryShapeGenerator(IsoscelesRightTriangleShape baseTriangle) {
        this.baseTriangle = baseTriangle;
    }

    /**
     * this is (Is,Ie)
     * q is the input parameter Inteval （Qs,Qe）
     * <p>
     * Equals Query: Is = Qs and Ie = Qe.
     * Starts Query: Is = Qs and Qs < Ie < Qe; as shown in Fig. 3a.
     * StartedBy Query: Is = Qs and Ie > Qe; as shown in Fig. 3b.
     * Meets Query: Is < Ie = Qs < Qe; as shown in Fig. 3c.
     * MetBy Query: Qs < Qe = Is < Ie; as shown in Fig. 3d.
     * Finishes Query: Qs < Is < Qe and Ie = Qe; as shown in Fig. 3e.
     * FinishedBy Query: Is < Qs and Ie = Qe; as shown in Fig. 3f.
     * Before Query: Is < Ie < Qs < Qe; as shown in Fig. 3a.
     * After Query: Qs < Qe < Is < Ie; as shown in Fig. 4b.
     * Overlaps Query: Is < Qs and Qs < Ie < Qe; as shown in Fig. 4c.
     * OverlappedBy Query: Qs < Is < Qe and Ie > Qe; as shown in      Fig. 4d.
     * During Query: Qs < Is < Ie < Qe; as shown in Fig. 4e.
     * Contains Query: Is < Qs < Qe < Ie; as shown in Fig. 4f.
     */
    public Shape equals(Interval q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }

    public Shape starts(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, s),
                new Vector2D(s, e));
    }

    public Shape startedBy(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, e),
                new Vector2D(s, baseTriangle.getVertex(0).getY()));
    }

    public Shape meets(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(baseTriangle.getVertex(0).getX(), s),
                new Vector2D(s, s));
    }

    public Shape metBy(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(e, e),
                new Vector2D(e, baseTriangle.getVertex(2).getY()));
    }

    public Shape finishes(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, e),
                new Vector2D(e, e));
    }

    public Shape finishedBy(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(baseTriangle.getVertex(0).getX(), s),
                new Vector2D(s, e));
    }

    public Shape before(Interval q) {
        double s = q.getLowerBound();
        return new RegionShape(
                baseTriangle.getVertex(1),
                new Vector2D(s, s));
    }

    public Shape after(Interval q) {
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(e, e),
                baseTriangle.getVertex(2));
    }

    public Shape overlaps(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(0, s),
                new Vector2D(s, e));
    }

    public Shape overlappedBy(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(s, e),
                new Vector2D(e, baseTriangle.getVertex(2).getY()));
    }

    public Shape during(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(s, s),
                new Vector2D(e, e));
    }

    public Shape contains(Interval q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(baseTriangle.getVertex(1).getX(), e),
                new Vector2D(s, baseTriangle.getVertex(2).getY()));
    }

    public Shape covers(Interval q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }

    public Shape coveredBy(Interval q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }
}
