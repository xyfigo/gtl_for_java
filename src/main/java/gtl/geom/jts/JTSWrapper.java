package gtl.geom.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import gtl.geom.Envelope2D;
import gtl.geom.Triangle;
import gtl.geom.Vector;
import gtl.geom.VectorSequence;

public class JTSWrapper {

    /**
     * 转换成JTS的Geometry
     * @param e2d
     * @return
     */
    public static Geometry toJTSGeometry(Envelope2D e2d){
        com.vividsolutions.jts.geom.Envelope e = new Envelope(
                e2d.getMinX(),e2d.getMaxX(),e2d.getMinY(),e2d.getMaxY());
        com.vividsolutions.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.toGeometry(e);
    }

    /**
     * 转换成JTS的Geometry
     * @param t
     * @return
     */
    public static Geometry toJTSGeometry(Triangle t){
        com.vividsolutions.jts.geom.GeometryFactory gf = new GeometryFactory();
        Coordinate[] coordinates = new Coordinate[4];
        int i=0;
        for(Vector v: t.getVertices()){
            if(v.getDimension()==2)
                coordinates[i] = new Coordinate(v.getX(),v.getY());
            else
                coordinates[i] = new Coordinate(v.getX(),v.getY(),v.getZ());
            i++;
        }
        coordinates[3] = new Coordinate(coordinates[0]) ;
        com.vividsolutions.jts.geom.Polygon p = gf.createPolygon(coordinates);
        return p;
    }

    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.Envelope e){
        double [] minXYZ=e.getLowCoordinates();
        double [] maxXYZ=e.getHighCoordinates();
        com.vividsolutions.jts.geom.Envelope jtse = new Envelope(
                minXYZ[0],maxXYZ[0],minXYZ[1],maxXYZ[1]);
        com.vividsolutions.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.toGeometry(jtse);
    }


    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.LineSegment e){
        Coordinate[] cc = new Coordinate[2];
        cc[0]=new Coordinate(e.getStartPoint().getX(),e.getStartPoint().getY());
        cc[1]=new Coordinate(e.getEndPoint().getX(),e.getEndPoint().getY());
        com.vividsolutions.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.createLineString(cc);
    }

    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.LineString e){
        VectorSequence vs = e.getVertices();
        int s = vs.size();
        Coordinate[] cc = new Coordinate[vs.size()];
        for (int i=0;i<s;++i)
            cc[i]=new Coordinate(vs.getX(i),vs.getY(i));
        com.vividsolutions.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.createLineString(cc);
    }

    /**
     * 转换成JTS的Geometry
     * @param
     * @return
     */
    public static Geometry toJTSGeometry(double x, double y){
        return new GeometryFactory().createPoint(new Coordinate(x,y));
    }
}
