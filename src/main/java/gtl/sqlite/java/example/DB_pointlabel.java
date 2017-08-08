package gtl.sqlite.java.example;

import java.io.Serializable;

//坐标点和类别类
public  class DB_pointlabel implements Serializable {
    private Double BH_X;
    private Double BH_Z;
    private Double BH_Y;
    private Integer BH_L;//类别

    public DB_pointlabel() {
    }

    public DB_pointlabel(Double BH_X, Double BH_Z, Double BH_Y, Integer BH_L) {
        this.BH_X = BH_X;
        this.BH_Z = BH_Z;
        this.BH_Y = BH_Y;
        this.BH_L = BH_L;
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

    public Integer getBH_L() {
        return BH_L;
    }

    public void setBH_L(Integer BH_L) {
        this.BH_L = BH_L;
    }


    @Override
    public String toString() {
        return BH_X + " " + BH_Y + " " + BH_Z + " " + BH_L + " ";
    }
}