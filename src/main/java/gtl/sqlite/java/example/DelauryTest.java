package gtl.sqlite.java.example;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.basic.BasicGraph;
import org.geotools.graph.util.delaunay.DelaunayEdge;
import org.geotools.graph.util.delaunay.DelaunayNode;
import org.geotools.graph.util.delaunay.DelaunayTriangulator;
import org.geotools.graph.util.delaunay.GraphViewer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DelauryTest {

    public static HashMap<String, DB_point> ReadResult(String filePath) {
        HashMap<String, DB_point> result = new HashMap<>();
        try {
            String encoding = "UTF-8";
            gtl.io.File file = new gtl.io.File(filePath);
            if (file.isFile() && file.exists()) {     //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);     //考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String t[] = lineTxt.split(" ");//“|”是转义字符,必须得加"\\"
                    result.put(t[0], new DB_point(Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3])));
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        HashMap<String, DB_point> Result = ReadResult("D:\\devs\\studies\\scala\\HelloWorld\\DBP.txt");
        List<DelaunayNode> nodes = new ArrayList<DelaunayNode>();
        for (Map.Entry<String, DB_point> entry : Result.entrySet()) {
           // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Coordinate coordinate = new Coordinate(entry.getValue().getBH_X(),entry.getValue().getBH_Y());
            DelaunayNode xyNode = new DelaunayNode();
            xyNode.setCoordinate(coordinate);
            nodes.add(xyNode);
        }
//        //添加四个控制点  因为左上角的点已经有了 所以只添加另外三个点
//        Coordinate coordinate2 = new Coordinate(5044.691824,45285.05923);
//        DelaunayNode xyNode2 = new DelaunayNode();
//        xyNode2.setCoordinate(coordinate2);
//        nodes.add(xyNode2);
//        Coordinate coordinate3 = new Coordinate(5044.691824,45127.57568);
//        DelaunayNode xyNode3 = new DelaunayNode();
//        xyNode3.setCoordinate(coordinate3);
//        nodes.add(xyNode3);
//        Coordinate coordinate4 = new Coordinate(4955.607439,45127.57568);
//        DelaunayNode xyNode4 = new DelaunayNode();
//        xyNode4.setCoordinate(coordinate4);
//        nodes.add(xyNode4);

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


        GraphViewer gpViewer = new GraphViewer();
        gpViewer.setGraph(delaunayTriangulator.getTriangulation());
        gpViewer.setSize(1500, 1500);
        gpViewer.setVisible(true);
        JFrame jFrame = new JFrame();
        jFrame.add(gpViewer);
        //jFrame.add(new JButton("dd"));
        jFrame.setSize(1500, 1500);
        jFrame.setVisible(true);

        //显示结果
        System.out.println("delaunayTriangulator.getTriangles()");
        System.out.println(delaunayTriangulator.getTriangles());
    }
}
