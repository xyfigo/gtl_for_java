package gtl.sqlite.java.example;

import java.io.Serializable;

//自定义三维数据点  想使用spark去计算的时候 其类必须是可序列化的 不然是无法进行海量计算
//可以使用DB_BOREHOLES_POSITION来代替这个类
public class DB_point implements Serializable {
    private Double BH_X;
    private Double BH_Z;
    private Double BH_Y;

    DB_point() {
    }

    DB_point(Double BH_X, Double BH_Y, Double BH_Z) {
        this.BH_X = BH_X;
        this.BH_Y = BH_Y;
        this.BH_Z = BH_Z;
    }

    DB_point(Double BH_X, Double BH_Y) {
        this.BH_X = BH_X;
        this.BH_Y = BH_Y;
    }

    public Double getBH_X() {
        return BH_X;
    }

    public void setBH_X(Double BH_X) {
        this.BH_X = BH_X;
    }

    public Double getBH_Z() {
        return BH_Z;
    }

    public void setBH_Z(Double BH_Z) {
        this.BH_Z = BH_Z;
    }

    public Double getBH_Y() {
        return BH_Y;
    }

    public void setBH_Y(Double BH_Y) {
        this.BH_Y = BH_Y;
    }


    @Override
    public String toString() {
        return BH_X + " " + BH_Y + " " + BH_Z + " ";
    }
}