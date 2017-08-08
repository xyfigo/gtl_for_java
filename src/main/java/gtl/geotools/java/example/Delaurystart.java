package gtl.geotools.java.example;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.basic.BasicGraph;
import org.geotools.graph.util.delaunay.DelaunayEdge;
import org.geotools.graph.util.delaunay.DelaunayNode;
import org.geotools.graph.util.delaunay.DelaunayTriangulator;
import org.geotools.graph.util.delaunay.GraphViewer;
import org.apache.commons.lang3.RandomUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextDouble;

public class Delaurystart {
    public static void main(String[] args) {
        List<DelaunayNode> nodes = new ArrayList<DelaunayNode>();

        for (int i = 0; i < 200; i++) {
            Coordinate coordinate = new Coordinate(nextDouble(4955.607439,5044.691824), nextDouble(45127.57568,45285.05923));
            DelaunayNode xyNode = new DelaunayNode();
            xyNode.setCoordinate(coordinate);
            nodes.add(xyNode);
        }

//        List<Edge> edges = new ArrayList<Edge>();
//        DelaunayEdge edge = new DelaunayEdge(nodes.get(0), nodes.get(1));
//        edges.add(edge);
//        BasicGraph basicGraph = new BasicGraph();
//        basicGraph.setNodes(nodes);
//        basicGraph.setEdges(edges);

        DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator();
        DelaunayNode[] delaunayNodes = new DelaunayNode[nodes.size()];
        System.out.println(nodes.toArray(delaunayNodes));
        delaunayTriangulator.setNodeArray(nodes.toArray(delaunayNodes));
        //构建三角网
        //delaunayTriangulator.getTriangulation();

        GraphViewer gpViewer = new GraphViewer();
        gpViewer.setGraph(delaunayTriangulator.getTriangulation());
        gpViewer.setSize(1500, 1500);
        gpViewer.setVisible(true);
        JFrame jFrame = new JFrame();
        jFrame.add(gpViewer);
        //jFrame.add(new JButton("dd"));
        jFrame.setSize(1500, 1500);
        jFrame.setVisible(true);

        System.out.println("delaunayTriangulator.getTriangles()");
        System.out.println(delaunayTriangulator.getTriangles());
    }
}
