package gtl.index.itree;

import gtl.common.Pair;
import gtl.geom.Timeline;
import gtl.geom.Triangle;
import gtl.index.shape.IsoscelesRightTriangleShape;
import gtl.ipc.MasterDescriptor;
import gtl.ipc.SlaveDescriptor;

import java.util.List;

public interface TTree {
    Triangle getRootTriangle();
    List<Pair<Triangle,SlaveDescriptor> > executePartition(MasterDescriptor md);
    Timeline executeSimilarityQuery(Timeline t);
}
