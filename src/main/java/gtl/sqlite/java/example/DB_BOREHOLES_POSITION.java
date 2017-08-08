package gtl.sqlite.java.example;

import java.io.Serializable;

/**
 * CREATE TABLE BOREHOLES_POSITION (BH_ID TEXT(50),	BH_X  REAL,BH_Y  REAL,BH_Z  REAL);
 */
public class DB_BOREHOLES_POSITION implements Serializable {
    private String BH_ID;
    private Double BH_X;
    private Double BH_Z;
    private Double BH_Y;

    DB_BOREHOLES_POSITION() {
    }

    DB_BOREHOLES_POSITION(String BH_ID, Double BH_X, Double BH_Y, Double BH_Z) {
        this.BH_ID = BH_ID;
        this.BH_X = BH_X;
        this.BH_Y = BH_Y;
        this.BH_Z = BH_Z;
    }

    DB_BOREHOLES_POSITION(String BH_ID, Double BH_X, Double BH_Y) {
        this.BH_ID = BH_ID;
        this.BH_X = BH_X;
        this.BH_Y = BH_Y;
    }

    DB_BOREHOLES_POSITION(Double BH_X, Double BH_Y) {
        this.BH_X = BH_X;
        this.BH_Y = BH_Y;
    }

    public String getBH_ID() {
        return BH_ID;
    }

    public void setBH_ID(String BH_ID) {
        this.BH_ID = BH_ID;
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
        return BH_ID + "  " + BH_X + " " + BH_Y + " " + BH_Z + " ";
    }
}
