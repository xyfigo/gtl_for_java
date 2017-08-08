package gtl.sqlite.java.example;

import java.io.Serializable;

/**
 * CREATE TABLE "BOREHOLES_DICTIONARY_LITHOLOGY" (
 * "BH_TYPE" TEXT(50),
 * "BH_LITHOLOGY" TEXT(50),
 * "BH_LITHOLOGY_COLOR_R" REAL,
 * "BH_LITHOLOGY_COLOR_G" REAL,
 * "BH_LITHOLOGY_COLOR_B" REAL,
 * "BH_LITHOLOGY_COLOR_A" REAL
 * );
 */

//类型和岩性 总共有29种组合  也就是去判断任意生成点的数据属于哪个类型
public class DB_BOREHOLES_DICTIONARY_LITHOLOGY implements Serializable {
    private String BH_TYPE;
    private String BH_LITHOLOGY;
    private Double BH_LITHOLOGY_COLOR_R;
    private Double BH_LITHOLOGY_COLOR_G;
    private Double BH_LITHOLOGY_COLOR_B;

    public String getBH_TYPE() {
        return BH_TYPE;
    }

    public void setBH_TYPE(String BH_TYPE) {
        this.BH_TYPE = BH_TYPE;
    }

    public String getBH_LITHOLOGY() {
        return BH_LITHOLOGY;
    }

    public void setBH_LITHOLOGY(String BH_LITHOLOGY) {
        this.BH_LITHOLOGY = BH_LITHOLOGY;
    }

    public Double getBH_LITHOLOGY_COLOR_R() {
        return BH_LITHOLOGY_COLOR_R;
    }

    public void setBH_LITHOLOGY_COLOR_R(Double BH_LITHOLOGY_COLOR_R) {
        this.BH_LITHOLOGY_COLOR_R = BH_LITHOLOGY_COLOR_R;
    }

    public Double getBH_LITHOLOGY_COLOR_G() {
        return BH_LITHOLOGY_COLOR_G;
    }

    public void setBH_LITHOLOGY_COLOR_G(Double BH_LITHOLOGY_COLOR_G) {
        this.BH_LITHOLOGY_COLOR_G = BH_LITHOLOGY_COLOR_G;
    }

    public Double getBH_LITHOLOGY_COLOR_B() {
        return BH_LITHOLOGY_COLOR_B;
    }

    public void setBH_LITHOLOGY_COLOR_B(Double BH_LITHOLOGY_COLOR_B) {
        this.BH_LITHOLOGY_COLOR_B = BH_LITHOLOGY_COLOR_B;
    }

    public Double getBH_LITHOLOGY_COLOR_A() {
        return BH_LITHOLOGY_COLOR_A;
    }

    public void setBH_LITHOLOGY_COLOR_A(Double BH_LITHOLOGY_COLOR_A) {
        this.BH_LITHOLOGY_COLOR_A = BH_LITHOLOGY_COLOR_A;
    }

    private Double BH_LITHOLOGY_COLOR_A;

    public DB_BOREHOLES_DICTIONARY_LITHOLOGY() {

    }

    public DB_BOREHOLES_DICTIONARY_LITHOLOGY(String BH_TYPE, String BH_LITHOLOGY, Double BH_LITHOLOGY_COLOR_R, Double BH_LITHOLOGY_COLOR_G, Double BH_LITHOLOGY_COLOR_A) {
        this.BH_TYPE = BH_TYPE;
        this.BH_LITHOLOGY = BH_LITHOLOGY;
        this.BH_LITHOLOGY_COLOR_A = BH_LITHOLOGY_COLOR_A;
        this.BH_LITHOLOGY_COLOR_B = BH_LITHOLOGY_COLOR_B;
        this.BH_LITHOLOGY_COLOR_G = BH_LITHOLOGY_COLOR_G;
        this.BH_LITHOLOGY_COLOR_R = BH_LITHOLOGY_COLOR_R;
    }

    @Override
    public String toString() {
        return BH_TYPE + " " + BH_LITHOLOGY + " " + BH_LITHOLOGY_COLOR_A + " " + BH_LITHOLOGY_COLOR_B + " " + BH_LITHOLOGY_COLOR_G + " " + BH_LITHOLOGY_COLOR_R + " ";
    }
}