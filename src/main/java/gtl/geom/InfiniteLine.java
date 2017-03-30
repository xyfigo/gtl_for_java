package gtl.geom;

import gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-26.
 * The primal way to specify a line L is by giving two distinct points, P0 and P1, on it.
 * In fact, this defines a finite line segment S going from P0 to P1 which are the endpoints
 * of S. This is how the Greeks understood straight lines, and it coincides with our natural
 * intuition for the most direct and shortest path between the two endpoints. This line can
 * then be extended indefinitely beyond either endpoint producing infinite rays in both directions.
 * When extended simultaneously beyond both ends, one gets the concept of an infinite line which
 * is how we often think of it today.
 *
 * reference :http://geomalgorithms.com/a02-_lines.html
 * reference :LineSegmentShape
 */
public class InfiniteLine implements gtl.io.Serializable, Comparable<InfiniteLine>{
    Vector startPoint;
    Vector endPoint;

    public InfiniteLine(Vector startPoint, Vector endPoint) {
        this.startPoint = (Vector) startPoint.clone();
        this.endPoint =(Vector)  endPoint.clone();
    }

    public InfiniteLine(double[] startPoint, double[] endPoint) {
        reset(startPoint, endPoint);
    }

    public InfiniteLine( ) {
        this.startPoint = new VectorImpl(0.0,0.0,0.0);
        this.endPoint =new VectorImpl(0.0,0.0,0.0);
    }

    @Override
    public Object clone() {
        return new InfiniteLine(this.startPoint,this.endPoint);
    }


    public void reset(Vector s, Vector e) {
        this.startPoint = (Vector) startPoint.clone();
        this.endPoint =(Vector)  endPoint.clone();
    }


    public void reset(double[] s, double[] e) {
        this.startPoint = new VectorImpl(s);
        this.endPoint =new VectorImpl(e);
    }

    @Override
    public void copyFrom(Object i) {
        if(i == null) return;
        if(i instanceof InfiniteLine){
            this.reset(((InfiniteLine)i).getStartPoint(),((InfiniteLine)i).getEndPoint());
        }
    }


    public Vector getStartPoint() {
        return this.startPoint;
    }


    public Vector getEndPoint() {
        return this.endPoint;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.startPoint.load(in);
        this.endPoint.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.startPoint.store(out);
        this.endPoint.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return this.startPoint.getByteArraySize()+this.endPoint.getByteArraySize();
    }

    @Override
    public int compareTo(InfiniteLine o) {
        return 0;
    }

    /**
     * 测试2D平面(XOY)上点与线的位置关系
     *
     * @param v
     * @return 0- 点在线上，也即是三点共线
     * -1- 点在线的左边
     * 1 - 点在线的右边
     */
    public int test(Vector v) {
        double[] P0;
        double[] P1;
        double[] P2;
        double d;
        Vector2D s, e;
        if (v.getDimension() == 2) {
            P0 = startPoint.getCoordinates();
            P1 = endPoint.getCoordinates();
            P2 = v.getCoordinates();

            d = (P1[0] - P0[0]) * (P2[1] - P0[1]) - (P2[0] - P0[0]) * (P1[1] - P0[1]);

            if (Math.abs(d) < MathSuits.EPSILON) return 0;
            if (d > 0) return -1;
            if (d < 0) return 1;
            return 0;
        } else {
            //XY
            s = startPoint.flapXY();
            e = endPoint.flapXY();
            if (s.equals(e)) {
                //XZ
                s = startPoint.flapXZ();
                e = endPoint.flapXZ();
                if (s.equals(e)) {//YZ
                    s = startPoint.flapYZ();
                    e = endPoint.flapYZ();
                    P0 = s.getCoordinates();
                    P1 = e.getCoordinates();
                    P2 = v.flapYZ().getCoordinates();
                    d = (P1[0] - P0[0]) * (P2[1] - P0[1]) - (P2[0] - P0[0]) * (P1[1] - P0[1]);

                    if (Math.abs(d) < MathSuits.EPSILON) return 0;
                    if (d > 0) return -1;
                    if (d < 0) return 1;
                    return 0;
                } else {//XZ
                    P0 = s.getCoordinates();
                    P1 = e.getCoordinates();
                    P2 = v.flapXZ().getCoordinates();
                    d = (P1[0] - P0[0]) * (P2[1] - P0[1]) - (P2[0] - P0[0]) * (P1[1] - P0[1]);

                    if (Math.abs(d) < MathSuits.EPSILON) return 0;
                    if (d > 0) return -1;
                    if (d < 0) return 1;
                    return 0;
                }
            } else {//XY
                P0 = s.getCoordinates();
                P1 = e.getCoordinates();
                P2 = v.flapXY().getCoordinates();
                d = (P1[0] - P0[0]) * (P2[1] - P0[1]) - (P2[0] - P0[0]) * (P1[1] - P0[1]);

                if (Math.abs(d) < MathSuits.EPSILON) return 0;
                if (d > 0) return -1;
                if (d < 0) return 1;
                return 0;
            }
        }
    }

    public InfiniteLine flap() {
        Vector2D s = startPoint.flap();
        Vector2D e = endPoint.flap();
        if (s.equals(e)) {
            return null;
        }
        return new InfiniteLine(s, e);
    }

}
