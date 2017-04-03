package gtl.index.itree;

import gtl.geom.Interval;
import gtl.index.shape.LineSegmentShape;
import gtl.index.shape.PointShape;
import gtl.index.shape.RegionShape;

import java.util.function.Function;

/**
 * Created by ZhenwenHe on 2017/4/3.
 * 查询执行器，传入构建好的三角形树
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
public class QueryExecutor {
    TriangleTree tree;

    public QueryExecutor(TriangleTree tree) {
        this.tree = tree;
    }

    public int equals(Interval q, Function<Interval, Boolean> f) {
        PointShape s = (PointShape) tree.queryShapeGenerator.equals(q);
        return tree.pointQuery(s, f);
    }

    public int starts(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.starts(q);
        return tree.lineQuery(s, f);
    }

    public int startedBy(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.startedBy(q);
        return tree.lineQuery(s, f);
    }

    public int meets(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.meets(q);
        return tree.lineQuery(s, f);
    }

    public int metBy(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.metBy(q);
        return tree.lineQuery(s, f);
    }

    public int finishes(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.finishes(q);
        return tree.lineQuery(s, f);
    }

    public int finishedBy(Interval q, Function<Interval, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.finishedBy(q);
        return tree.lineQuery(s, f);
    }

    public int before(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.before(q);
        return tree.regionQuery(s, f);
    }

    public int after(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.after(q);
        return tree.regionQuery(s, f);
    }

    public int overlaps(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.overlaps(q);
        return tree.regionQuery(s, f);
    }

    public int overlappedBy(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.overlappedBy(q);
        return tree.regionQuery(s, f);
    }

    public int during(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.during(q);
        return tree.regionQuery(s, f);
    }

    public int contains(Interval q, Function<Interval, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.contains(q);
        return tree.regionQuery(s, f);
    }

    public int covers(Interval q, Function<Interval, Boolean> f) {
        return equals(q, f);
    }

    public int coveredBy(Interval q, Function<Interval, Boolean> f) {
        return equals(q, f);
    }
}
