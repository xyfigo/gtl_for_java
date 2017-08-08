package gtl.sqlite.java.example;

import java.util.LinkedList;
import java.util.List;

/**
 * 计算点是否在三角形区域内部
 * http://blog.csdn.net/u011489043/article/details/78213650
 * http://www.cnblogs.com/TenosDoIt/p/4024413.html
 * https://www.cnblogs.com/graphics/archive/2010/08/05/1793393.html
 */
public class STriangulation {
    /**
     * 具体某点是否在已经剖分的三角形内部
     */
    public static DB_ThreePoint PisInListTriangle(DB_BOREHOLES_POSITION p, List<DB_ThreePoint> TriangleList) {
        DB_ThreePoint result = new DB_ThreePoint();
        DB_BOREHOLES_POSITION AB, AC, AP;
        for (DB_ThreePoint aTriangleList : TriangleList) {
            AB = new DB_BOREHOLES_POSITION(aTriangleList.getB().getBH_X() - aTriangleList.getA().getBH_X(), aTriangleList.getB().getBH_Y() - aTriangleList.getA().getBH_Y());
            AC = new DB_BOREHOLES_POSITION(aTriangleList.getC().getBH_X() - aTriangleList.getA().getBH_X(), aTriangleList.getC().getBH_Y() - aTriangleList.getA().getBH_Y());
            AP = new DB_BOREHOLES_POSITION(p.getBH_X() - aTriangleList.getA().getBH_X(), p.getBH_Y() - aTriangleList.getA().getBH_Y());
            double dot00 = dotProduct(AC, AC);
            double dot01 = dotProduct(AC, AB);
            double dot02 = dotProduct(AC, AP);
            double dot11 = dotProduct(AB, AB);
            double dot12 = dotProduct(AB, AP);
            double inverDeno = 1 / (dot00 * dot11 - dot01 * dot01);
            // 计算重心坐标
            double u = (dot11 * dot02 - dot01 * dot12) * inverDeno;
            double v = (dot00 * dot12 - dot01 * dot02) * inverDeno;
            //return (u >= 0) && (v >= 0) && (u + v < 1);
            if ((u >= 0) && (v >= 0) && (u + v < 1)) {
                result.setA(aTriangleList.getA());
                result.setB(aTriangleList.getB());
                result.setC(aTriangleList.getC());
                return result;
            }
        }
        return result;
    }

    /**
     * 参数
     * P:具体某个点
     * ABC：三角形的三个顶点
     */

    public static boolean PisInTriangle(DB_BOREHOLES_POSITION p, DB_BOREHOLES_POSITION a, DB_BOREHOLES_POSITION b, DB_BOREHOLES_POSITION c) {
        DB_BOREHOLES_POSITION AB, AC, AP;
        AB = new DB_BOREHOLES_POSITION(b.getBH_X() - a.getBH_X(), b.getBH_Y() - a.getBH_Y());
        AC = new DB_BOREHOLES_POSITION(c.getBH_X() - a.getBH_X(), c.getBH_Y() - a.getBH_Y());
        AP = new DB_BOREHOLES_POSITION(p.getBH_X() - a.getBH_X(), p.getBH_Y() - a.getBH_Y());
        double dot00 = dotProduct(AC, AC);
        double dot01 = dotProduct(AC, AB);
        double dot02 = dotProduct(AC, AP);
        double dot11 = dotProduct(AB, AB);
        double dot12 = dotProduct(AB, AP);
        double inverDeno = 1 / (dot00 * dot11 - dot01 * dot01);
        // 计算重心坐标
        double u = (dot11 * dot02 - dot01 * dot12) * inverDeno;
        double v = (dot00 * dot12 - dot01 * dot02) * inverDeno;
        return (u >= 0) && (v >= 0) && (u + v < 1);
    }

    public static double dotProduct(DB_BOREHOLES_POSITION p1, DB_BOREHOLES_POSITION p2) {
        return p1.getBH_X() * p2.getBH_X() + p1.getBH_Y() * p2.getBH_Y();
    }

    public static void main(String[] args) {
        List<DB_ThreePoint> LDBT = new LinkedList<>();

        DB_BOREHOLES_POSITION P = new DB_BOREHOLES_POSITION(0.0, 0.5);
        DB_BOREHOLES_POSITION P2 = new DB_BOREHOLES_POSITION(0.0, -0.5);

        DB_BOREHOLES_POSITION A = new DB_BOREHOLES_POSITION(1.0, 0.0);
        DB_BOREHOLES_POSITION B = new DB_BOREHOLES_POSITION(0.0, 1.0);
        DB_BOREHOLES_POSITION C = new DB_BOREHOLES_POSITION(-1.0, 0.0);

        DB_BOREHOLES_POSITION A2 = new DB_BOREHOLES_POSITION(1.0, 0.0);
        DB_BOREHOLES_POSITION B2 = new DB_BOREHOLES_POSITION(0.0, -1.0);
        DB_BOREHOLES_POSITION C2 = new DB_BOREHOLES_POSITION(-1.0, 0.0);

        DB_ThreePoint D = new DB_ThreePoint(A, B, C);
        DB_ThreePoint D2 = new DB_ThreePoint(A2, B2, C2);

        LDBT.add(D);
        LDBT.add(D2);

        if (PisInTriangle(P, A, B, C)) {
            System.out.println("点P在三角形ABC内部！");
        } else {
            System.out.println("点P不在三角形ABC内部！");
        }
        if (PisInTriangle(P2, A, B, C)) {
            System.out.println("点P2在三角形ABC内部！");
        } else {
            System.out.println("点P2不在三角形ABC内部！");
        }

        if (PisInListTriangle(P, LDBT) != null) {
            System.out.println("点P在LDBT内部！");
        } else {
            System.out.println("点P不在LDBT内部！");
        }
        if (PisInListTriangle(P2, LDBT) != null) {
            System.out.println("点P2在LDBT内部！");
        } else {
            System.out.println("点P2不在LDBT内部！");
        }

    }
}
