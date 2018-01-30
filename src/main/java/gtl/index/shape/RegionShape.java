package gtl.index.shape;

import gtl.geom.Envelope;
import gtl.geom.EnvelopeImpl;
import gtl.geom.Geom3DSuits;
import gtl.geom.Vector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by ZhenwenHe on 2016/12/7.
 */
public class RegionShape extends EnvelopeImpl implements Shape {
    private static final long serialVersionUID = 1L;

    public RegionShape() {
        super();
    }

    public RegionShape(double[] low, double[] high) {
        super(low, high);
    }

    public RegionShape(Envelope e) {
       super(e.getLowCoordinates(), e.getHighCoordinates());
    }

    public RegionShape(Vector leftBottom, Vector rightTop) {
        super(leftBottom.getCoordinates(), rightTop.getCoordinates());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean intersectsShape(Shape in) {
        if (in == null) return false;
        if (in instanceof RegionShape) {
            return this.intersectsRegion((RegionShape) in);
        }

        if (in instanceof LineSegmentShape) {
            return this.intersectsLineSegment((LineSegmentShape) in);
        }

        if (in instanceof PointShape) {
            return this.containsPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public boolean containsShape(Shape in) {
        if (in == null) return false;

        if (in instanceof RegionShape) {
            return this.containsRegion((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.containsPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public boolean touchesShape(Shape in) {
        if (in == null) return false;

        if (in instanceof RegionShape) {
            return this.touchesRegion((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.touchesPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public Vector getCenter() {
        PointShape p = ShapeSuits.createPoint();

        int dims = this.getDimension();
        p.makeDimension(dims);
        double[] cc = p.getCoordinates();
        for (int i = 0; i < dims; i++) {
            cc[i] = (this.getLowCoordinate(i) + this.getHighCoordinate(i)) / 2.0;
        }
        return p.getCenter();
    }

    @Override
    public Envelope getMBR() {
        return (Envelope)this.clone();
    }

    @Override
    public double getArea() {

        double area = 1.0;
        int dims = this.getDimension();
        for (int i = 0; i < dims; ++i) {
            area *= (this.getHighCoordinate(i) - this.getLowCoordinate(i));
        }

        return area;
    }

    @Override
    public double getMinimumDistance(Shape in) {

        if (in instanceof RegionShape) {
            return this.getMinimumDistance((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.getMinimumDistance((PointShape) in);
        }

        return 0;
    }

    @Override
    public Object clone() {
        return new RegionShape(getLowCoordinates(), getHighCoordinates());
    }


    public boolean intersectsRegion(RegionShape in) {
        return intersects(in.getMBR());
    }


    public boolean containsRegion(RegionShape in) {
        return contains(in.getMBR());
    }


    public boolean touchesRegion(RegionShape in) {
        return touches(in.getMBR());
    }


    public double getMinimumDistance(RegionShape e) {
        if (e == null) return Double.MAX_VALUE;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return Double.MAX_VALUE;

        double ret = 0.0;

        for (int i = 0; i < dims; ++i) {
            double x = 0.0;

            if (e.getHighCoordinate(i) < this.getLowCoordinate(i)) {
                x = Math.abs(e.getHighCoordinate(i) - this.getLowCoordinate(i));
            } else if (this.getHighCoordinate(i) < e.getLowCoordinate(i)) {
                x = Math.abs(e.getLowCoordinate(i) - this.getHighCoordinate(i));
            }

            ret += x * x;
        }

        return Math.sqrt(ret);
    }


    public boolean containsPoint(PointShape in) {
        return contains(in.getCenter());
    }


    public boolean touchesPoint(PointShape in) {
        return touches(in.getCenter());
    }


    public boolean intersectsLineSegment(LineSegmentShape e) {
        if (e == null) return false;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return false;

        assert dims == 2;

        // there may be a more efficient method, but this suffices for now
        PointShape ll = ShapeSuits.createPoint(this.getLowCoordinates());
        PointShape ur = ShapeSuits.createPoint(this.getHighCoordinates());
        // fabricate ul and lr coordinates and points
        PointShape ul = ShapeSuits.createPoint(this.getLowCoordinate(0), this.getHighCoordinate(1));
        PointShape lr = ShapeSuits.createPoint(this.getHighCoordinate(0), this.getLowCoordinate(1));

        // Points/LineSegmentShape for the segment
        PointShape p1 = ShapeSuits.createPoint(e.getStartCoordinates());
        PointShape p2 = ShapeSuits.createPoint(e.getEndCoordinates());


        //Check whether either or both the endpoints are within the region OR
        //whether any of the bounding segments of the RegionShape intersect the segment
        return (this.containsPoint(p1) || this.containsPoint(p2) ||
                e.intersectsShape(ShapeSuits.createLineSegment(ll, ul)) || e.intersectsShape(ShapeSuits.createLineSegment(ul, ur)) ||
                e.intersectsShape(ShapeSuits.createLineSegment(ur, lr)) || e.intersectsShape(ShapeSuits.createLineSegment(lr, ll)));
    }

    public double getMinimumDistance(PointShape p) {

        if (p == null) return Double.MAX_VALUE;
        int dims = this.getDimension();
        if (dims != p.getDimension()) return Double.MAX_VALUE;


        double ret = 0.0;

        for (int i = 0; i < dims; ++i) {
            if (p.getCoordinate(i) < this.getLowCoordinate(i)) {
                ret += Math.pow(this.getLowCoordinate(i) - p.getCoordinate(i), 2.0);
            } else if (p.getCoordinate(i) > this.getHighCoordinate(i)) {
                ret += Math.pow(p.getCoordinate(i) - this.getHighCoordinate(i), 2.0);
            }
        }

        return Math.sqrt(ret);
    }


    public RegionShape getIntersectingRegion(RegionShape r) {
        return new RegionShape(getIntersectingEnvelope(r.getMBR()));
    }



    public void combineRegion(RegionShape in) {
        combine(in.getMBR());
    }


    public void combinePoint(PointShape in) {
        combine(in.getCenter());
    }


    public RegionShape getCombinedRegion(RegionShape in) {

        RegionShape r = (RegionShape) this.clone();
        r.combineRegion(in);
        return r;
    }

    /**
     * 按照X,Y,Z,W方向由小向大排列
     * 如果是二维，则索引需要为0,1,2,3
     * 如果为三维，则索引序号为0,1,2,4,5,6,7,
     * 如果为四维，则索引序号为0~15
     * @return
     */
    public RegionShape subregion(){
        return null;
    }

}

