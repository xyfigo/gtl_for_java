package gtl.sqlite.java.example;


/**
 * CREATE TABLE "BOREHOLES_LITHOLOGY" (
 * "BH_ID" TEXT(50),
 * "BH_TYPE" TEXT(50),
 * "BH_DEPTH" REAL,
 * "BH_THICKNESS" REAL,
 * "BH_LITHOLOGY" TEXT(50)
 * );
 */

public class DB_BOREHOLES_LITHOLOGY {

    private String BH_ID;
    private Double BH_DEPTH;
    private Double BH_THICKNESS;
    private String BH_LITHOLOGY;
    private String BH_TYPE;
    private Double BH_Mid;  //中间点

    DB_BOREHOLES_LITHOLOGY() {
    }

    DB_BOREHOLES_LITHOLOGY(String BH_ID, String BH_TYPE, Double BH_DEPTH, Double BH_THICKNESS, String BH_LITHOLOGY, Double BH_Mid) {
        this.BH_ID = BH_ID;
        this.BH_TYPE = BH_TYPE;
        this.BH_DEPTH = BH_DEPTH;
        this.BH_THICKNESS = BH_THICKNESS;
        this.BH_LITHOLOGY = BH_LITHOLOGY;
        this.BH_Mid = BH_Mid;
    }

    public String getBH_TYPE() {
        return BH_TYPE;
    }

    public void setBH_TYPE(String BH_TYPE) {
        this.BH_TYPE = BH_TYPE;
    }


    public String getBH_ID() {
        return BH_ID;
    }

    public void setBH_ID(String BH_ID) {
        this.BH_ID = BH_ID;
    }

    public Double getBH_DEPTH() {
        return BH_DEPTH;
    }

    public void setBH_DEPTH(Double BH_DEPTH) {
        this.BH_DEPTH = BH_DEPTH;
    }

    public Double getBH_THICKNESS() {
        return BH_THICKNESS;
    }

    public void setBH_THICKNESS(Double BH_THICKNESS) {
        this.BH_THICKNESS = BH_THICKNESS;
    }

    public String getBH_LITHOLOGY() {
        return BH_LITHOLOGY;
    }

    public void setBH_LITHOLOGY(String BH_LITHOLOGY) {
        this.BH_LITHOLOGY = BH_LITHOLOGY;
    }


    public Double getBH_Mid() {
        return BH_Mid;
    }

    public void setBH_Mid(Double BH_Mid) {
        this.BH_Mid = BH_Mid;
    }

    @Override
    public String toString() {
        return this.BH_ID + "\t" + this.BH_TYPE + "\t" + this.BH_DEPTH + "\t" + this.BH_THICKNESS + "\t" + this.BH_LITHOLOGY + "\t" + this.BH_Mid;
    }
}