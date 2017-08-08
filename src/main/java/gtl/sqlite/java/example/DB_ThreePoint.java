package gtl.sqlite.java.example;

import java.io.Serializable;

/**
 * 三角剖分 三角形存储格式
 */
public class DB_ThreePoint implements Serializable {
    private DB_BOREHOLES_POSITION A;
    private DB_BOREHOLES_POSITION B;
    private DB_BOREHOLES_POSITION C;

    DB_ThreePoint() {
    }

    DB_ThreePoint(DB_BOREHOLES_POSITION A, DB_BOREHOLES_POSITION B, DB_BOREHOLES_POSITION C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public DB_BOREHOLES_POSITION getA() {
        return A;
    }

    public void setA(DB_BOREHOLES_POSITION a) {
        A = a;
    }

    public DB_BOREHOLES_POSITION getB() {
        return B;
    }

    public void setB(DB_BOREHOLES_POSITION b) {
        B = b;
    }

    public DB_BOREHOLES_POSITION getC() {
        return C;
    }

    public void setC(DB_BOREHOLES_POSITION c) {
        C = c;
    }

}