package gtl.geom;

/**
 * Created by ZhenwenHe on 2017/3/13.
 * V0V1==V0V2
 */
public class IsoscelesTriangle extends TriangleImpl {
    public IsoscelesTriangle() {
    }

    public IsoscelesTriangle(Vector[] vertices) {
        super(vertices);
    }

    public IsoscelesTriangle(Vector v0, Vector v1, Vector v2) {
        super(v0, v1, v2);
    }

    public Triangle leftTriangle(){
        // (V1+V2)/2.0
        Vector m = vertices[1].add(vertices[2]).divide(2.0);
        return new TriangleImpl(m, vertices[0], vertices[1]);
    }
    public Triangle rightTriangle(){
        // m=(V1+V2)/2.0
        Vector m = vertices[1].add(vertices[2]).divide(2.0);
        return new TriangleImpl(m, vertices[2], vertices[0]);
    }
}
