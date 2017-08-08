//package gtl.sqlite.java.example;
//
//import java.io.*;
//import java.sql.*;
//import java.util.*;
//
//
///**
// * CREATE TABLE "BOREHOLES_DICTIONARY_LITHOLOGY" (
// * "BH_TYPE" TEXT(50),
// * "BH_LITHOLOGY" TEXT(50),
// * "BH_LITHOLOGY_COLOR_R" REAL,
// * "BH_LITHOLOGY_COLOR_G" REAL,
// * "BH_LITHOLOGY_COLOR_B" REAL,
// * "BH_LITHOLOGY_COLOR_A" REAL
// * );
// */
////类型和岩性 总共有29种组合  也就是去判断任意生成点的数据属于哪个类型
//class DB_BOREHOLES_DICTIONARY_LITHOLOGY {
//    private String BH_TYPE;
//    private String BH_LITHOLOGY;
//    private Double BH_LITHOLOGY_COLOR_R;
//    private Double BH_LITHOLOGY_COLOR_G;
//    private Double BH_LITHOLOGY_COLOR_B;
//
//    public String getBH_TYPE() {
//        return BH_TYPE;
//    }
//
//    public void setBH_TYPE(String BH_TYPE) {
//        this.BH_TYPE = BH_TYPE;
//    }
//
//    public String getBH_LITHOLOGY() {
//        return BH_LITHOLOGY;
//    }
//
//    public void setBH_LITHOLOGY(String BH_LITHOLOGY) {
//        this.BH_LITHOLOGY = BH_LITHOLOGY;
//    }
//
//    public Double getBH_LITHOLOGY_COLOR_R() {
//        return BH_LITHOLOGY_COLOR_R;
//    }
//
//    public void setBH_LITHOLOGY_COLOR_R(Double BH_LITHOLOGY_COLOR_R) {
//        this.BH_LITHOLOGY_COLOR_R = BH_LITHOLOGY_COLOR_R;
//    }
//
//    public Double getBH_LITHOLOGY_COLOR_G() {
//        return BH_LITHOLOGY_COLOR_G;
//    }
//
//    public void setBH_LITHOLOGY_COLOR_G(Double BH_LITHOLOGY_COLOR_G) {
//        this.BH_LITHOLOGY_COLOR_G = BH_LITHOLOGY_COLOR_G;
//    }
//
//    public Double getBH_LITHOLOGY_COLOR_B() {
//        return BH_LITHOLOGY_COLOR_B;
//    }
//
//    public void setBH_LITHOLOGY_COLOR_B(Double BH_LITHOLOGY_COLOR_B) {
//        this.BH_LITHOLOGY_COLOR_B = BH_LITHOLOGY_COLOR_B;
//    }
//
//    public Double getBH_LITHOLOGY_COLOR_A() {
//        return BH_LITHOLOGY_COLOR_A;
//    }
//
//    public void setBH_LITHOLOGY_COLOR_A(Double BH_LITHOLOGY_COLOR_A) {
//        this.BH_LITHOLOGY_COLOR_A = BH_LITHOLOGY_COLOR_A;
//    }
//
//    private Double BH_LITHOLOGY_COLOR_A;
//
//    public DB_BOREHOLES_DICTIONARY_LITHOLOGY() {
//
//    }
//
//    public DB_BOREHOLES_DICTIONARY_LITHOLOGY(String BH_TYPE, String BH_LITHOLOGY, Double BH_LITHOLOGY_COLOR_R, Double BH_LITHOLOGY_COLOR_G, Double BH_LITHOLOGY_COLOR_A) {
//        this.BH_TYPE = BH_TYPE;
//        this.BH_LITHOLOGY = BH_LITHOLOGY;
//        this.BH_LITHOLOGY_COLOR_A = BH_LITHOLOGY_COLOR_A;
//        this.BH_LITHOLOGY_COLOR_B = BH_LITHOLOGY_COLOR_B;
//        this.BH_LITHOLOGY_COLOR_G = BH_LITHOLOGY_COLOR_G;
//        this.BH_LITHOLOGY_COLOR_R = BH_LITHOLOGY_COLOR_R;
//    }
//
//    @Override
//    public String toString() {
//        return BH_TYPE + " " + BH_LITHOLOGY + " " + BH_LITHOLOGY_COLOR_A + " " + BH_LITHOLOGY_COLOR_B + " " + BH_LITHOLOGY_COLOR_G + " " + BH_LITHOLOGY_COLOR_R + " ";
//    }
//}
//
///**
// * CREATE TABLE "BOREHOLES_LITHOLOGY" (
// * "BH_ID" TEXT(50),
// * "BH_TYPE" TEXT(50),
// * "BH_DEPTH" REAL,
// * "BH_THICKNESS" REAL,
// * "BH_LITHOLOGY" TEXT(50)
// * );
// */
//class DB_BOREHOLES_LITHOLOGY {
//
//    private String BH_ID;
//    private Double BH_DEPTH;
//    private Double BH_THICKNESS;
//    private String BH_LITHOLOGY;
//    private String BH_TYPE;
//    private Double BH_Mid;//垂直距离的处理 先计算中间点
//
//    public DB_BOREHOLES_LITHOLOGY() {
//    }
//
//    public DB_BOREHOLES_LITHOLOGY(String BH_ID, String BH_TYPE, Double BH_DEPTH, Double BH_THICKNESS, String BH_LITHOLOGY, Double BH_Mid) {
//        this.BH_ID = BH_ID;
//        this.BH_TYPE = BH_TYPE;
//        this.BH_DEPTH = BH_DEPTH;
//        this.BH_THICKNESS = BH_THICKNESS;
//        this.BH_LITHOLOGY = BH_LITHOLOGY;
//        this.BH_Mid = BH_Mid;
//    }
//
//    public String getBH_TYPE() {
//        return BH_TYPE;
//    }
//
//    public void setBH_TYPE(String BH_TYPE) {
//        this.BH_TYPE = BH_TYPE;
//    }
//
//
//    public String getBH_ID() {
//        return BH_ID;
//    }
//
//    public void setBH_ID(String BH_ID) {
//        this.BH_ID = BH_ID;
//    }
//
//    public Double getBH_DEPTH() {
//        return BH_DEPTH;
//    }
//
//    public void setBH_DEPTH(Double BH_DEPTH) {
//        this.BH_DEPTH = BH_DEPTH;
//    }
//
//    public Double getBH_THICKNESS() {
//        return BH_THICKNESS;
//    }
//
//    public void setBH_THICKNESS(Double BH_THICKNESS) {
//        this.BH_THICKNESS = BH_THICKNESS;
//    }
//
//    public String getBH_LITHOLOGY() {
//        return BH_LITHOLOGY;
//    }
//
//    public void setBH_LITHOLOGY(String BH_LITHOLOGY) {
//        this.BH_LITHOLOGY = BH_LITHOLOGY;
//    }
//
//
//    public Double getBH_Mid() {
//        return BH_Mid;
//    }
//
//    public void setBH_Mid(Double BH_Mid) {
//        this.BH_Mid = BH_Mid;
//    }
//
//    @Override
//    public String toString() {
//        return this.BH_ID + "\t" + this.BH_TYPE + "\t" + this.BH_DEPTH + "\t" + this.BH_THICKNESS + "\t" + this.BH_LITHOLOGY + "\t" + this.BH_Mid;
//    }
//}
//
////CREATE TABLE BOREHOLES_POSITION (BH_ID TEXT(50),	BH_X  REAL,BH_Y  REAL,BH_Z  REAL);
//class DB_BOREHOLES_POSITION {
//    private String BH_ID;
//    private Double BH_X;
//    private Double BH_Z;
//    private Double BH_Y;
//
//    public DB_BOREHOLES_POSITION() {
//    }
//
//    public DB_BOREHOLES_POSITION(String BH_ID, Double BH_X, Double BH_Z, Double BH_Y) {
//        this.BH_ID = BH_ID;
//        this.BH_X = BH_X;
//        this.BH_Z = BH_Z;
//        this.BH_Y = BH_Y;
//    }
//
//    public String getBH_ID() {
//        return BH_ID;
//    }
//
//    public void setBH_ID(String BH_ID) {
//        this.BH_ID = BH_ID;
//    }
//
//    public Double getBH_X() {
//        return BH_X;
//    }
//
//    public void setBH_X(Double BH_X) {
//        this.BH_X = BH_X;
//    }
//
//    public Double getBH_Z() {
//        return BH_Z;
//    }
//
//    public void setBH_Z(Double BH_Z) {
//        this.BH_Z = BH_Z;
//    }
//
//    public Double getBH_Y() {
//        return BH_Y;
//    }
//
//    public void setBH_Y(Double BH_Y) {
//        this.BH_Y = BH_Y;
//    }
//
//    @Override
//    public String toString() {
//        return BH_ID + "  " + BH_X + " " + BH_Y + " " + BH_Z + " ";
//    }
//}
//
////自定义三维数据点
//class DB_point {
//    private Double BH_X;
//    private Double BH_Z;
//    private Double BH_Y;
//
//    public DB_point() {
//    }
//
//    public DB_point(Double BH_X, Double BH_Z, Double BH_Y) {
//        this.BH_X = BH_X;
//        this.BH_Z = BH_Z;
//        this.BH_Y = BH_Y;
//    }
//
//    public Double getBH_X() {
//        return BH_X;
//    }
//
//    public void setBH_X(Double BH_X) {
//        this.BH_X = BH_X;
//    }
//
//    public Double getBH_Z() {
//        return BH_Z;
//    }
//
//    public void setBH_Z(Double BH_Z) {
//        this.BH_Z = BH_Z;
//    }
//
//    public Double getBH_Y() {
//        return BH_Y;
//    }
//
//    public void setBH_Y(Double BH_Y) {
//        this.BH_Y = BH_Y;
//    }
//
//
//    @Override
//    public String toString() {
//        return BH_X + " " + BH_Y + " " + BH_Z + " ";
//    }
//}
//
//public class ReadData {
//    //划分随机数据使用
//    private static double MaxX = Double.MIN_VALUE;
//    private static double MaxY = Double.MIN_VALUE;
//    private static double MaxZ = Double.MIN_VALUE;
//    private static double MinX = Double.MAX_VALUE;
//    private static double MinY = Double.MAX_VALUE;
//    private static double MinZ = Double.MAX_VALUE;
//    private static double MaxDepth = Double.MIN_VALUE;
//
//    public static Long readTxtFileIntoStringArrList(String filePath) {
//        Long a = 0L;
//        try {
//            String encoding = "UTF-8";
//            gtl.io.File file = new gtl.io.File(filePath);
//            if (file.isFile() && file.exists()) {     //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(
//                        new FileInputStream(file), encoding);     //考虑到编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//                    a++;
//                }
//                bufferedReader.close();
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//
//        return a;
//    }
//
//    //生成网格数据    栅格大小0.1    先不考虑标高    直接全部设置成0   原始点(x,y,0.0)
//    public static List<DB_point> GetRandomValue(double MaxXP, double MaxYP, double MaxZP, double MinXP, double MinYP, double MinZP, double MaxDepthP) throws InterruptedException, IOException {
//        System.out.println("DB_BOREHOLES_POSITIONMaxX: " + MaxXP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMaxY: " + MaxYP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMaxZ: " + MaxZP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMinX: " + MinXP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMinY: " + MinYP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMinZ：" + MinZP + " ");
//        System.out.println("DB_BOREHOLES_POSITIONMinDepth：" + MaxDepthP + " ");
//
//        DB_point DBP = new DB_point();
//        List<DB_point> result = new LinkedList<>();
//
//        // PrintWriter writer = new PrintWriter(new FileOutputStream(new gtl.io.File("TestData.txt"), true));
//        int max = 29;
//        int min = 1;
//
//        /* 写入Txt文件 */
//        File writename = new File("TestData.txt"); // 相对路径，如果没有则要建立一个新的TestData.txt文件
//        writename.createNewFile(); //创建新文件
//        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//
//
//        /**
//         * 计数器
//         * 栅格大小
//         * */
//        int ii = 0;
//        int jj = 0;
//        int kk = 0;
//
//        for (double i = MinXP; i <= MaxXP; i += 1, ii++) {
//            for (double j = MinYP; j < MaxYP; j += 1, jj++) {
//                for (double k = 0; k < MaxDepthP; k += 1, kk++) {
//                    DBP.setBH_X(i);
//                    DBP.setBH_Y(j);
//                    DBP.setBH_Z(k);
////                    System.out.println(DBP.toString());
////                    TimeUnit.SECONDS.sleep(3);
//                    result.add(DBP);
//                    Random random = new Random();
//                    int s = random.nextInt(max) % (max - min + 1) + min;
//                    //writer.append(Integer.toString(s) + " " + "1:" + Double.toString(i) + " " + "2:" + Double.toString(j) + " " + "3:" + Double.toString(k));
////                    writer.append(Integer.toString(s) + " "+ Double.toString(i) + " " + Double.toString(j) + " " + Double.toString(k));
////                    writer.println();
//
//                    out.write(Integer.toString(s) + " " + Double.toString(i) + " " + Double.toString(j) + " " + Double.toString(k)); // \r\n即为换行
//                    out.write("\n");
//                    out.flush(); // 把缓存区内容压入文件
//
//                    DBP = new DB_point();
//                }
//            }
//        }
//        //最后记得关闭文件
//        //writer.close();
//        out.close();
//        System.out.println("iicount:" + ii + "jjcount:" + (jj / ii) + "kkcount:" + (kk / jj));
//        System.out.println("随机生成 " + result.size() + "条数据");
//        return result;
//    }
//
//    //对象之间赋值操作  要是想要通用就参考链接即可  http://bijian1013.iteye.com/blog/2041392
//    public static void copyvalue(DB_BOREHOLES_LITHOLOGY v1, DB_BOREHOLES_LITHOLOGY v2) {
//        v1.setBH_ID(v2.getBH_ID());
//        v1.setBH_Mid(v2.getBH_Mid());
//        v1.setBH_LITHOLOGY(v2.getBH_LITHOLOGY());
//        v1.setBH_THICKNESS(v2.getBH_THICKNESS());
//        v1.setBH_DEPTH(v2.getBH_DEPTH());
//        v1.setBH_TYPE(v2.getBH_TYPE());
//    }
//
//    public static void copyvalue2(DB_BOREHOLES_POSITION v1, DB_BOREHOLES_POSITION v2) {
//        v1.setBH_ID(v2.getBH_ID());
//        v1.setBH_X(v2.getBH_X());
//        v1.setBH_Y(v2.getBH_Y());
//        v1.setBH_Z(v2.getBH_Z());
//    }
//
//
//    //计算两点之间的距离
//    public static List<Double> GetDistance(List<DB_point> A, List<DB_point> B) {
//        List<Double> resultList = new LinkedList<>();
//        double result;
//        for (int i = 0; i < A.size(); i++) {
//            for (int j = 0; j < B.size(); j++) {
//                result = Math.sqrt(Math.pow(A.get(i).getBH_X() - B.get(j).getBH_X(), 2) + Math.pow(A.get(i).getBH_Y() - B.get(j).getBH_Y(), 2) + Math.pow(A.get(i).getBH_X() - B.get(j).getBH_X(), 2));
//                resultList.add(result);
//            }
//        }
//        return resultList;
//    }
//
//    //计算两点之间的距离 直接写成libSVM的格式
//    public static void GetDistancelibSVM(List<DB_point> A, List<DB_point> B) {
//        List<Double> resultList = new LinkedList<>();
//        double result;
//        for (int i = 0; i < A.size(); i++) {
//            for (int j = 0; j < B.size(); j++) {
//                result = Math.sqrt(Math.pow(A.get(i).getBH_X() - B.get(j).getBH_X(), 2) + Math.pow(A.get(i).getBH_Y() - B.get(j).getBH_Y(), 2) + Math.pow(A.get(i).getBH_X() - B.get(j).getBH_X(), 2));
//                //  System.out.println(result);
//                resultList.add(result);
//            }
//        }
//    }
//
//    //从数据库获取数据
//
//    /**
//     * BH_TYPE和BH_LITHOLOGY作为一个分类标准
//     */
//    public static HashMap<String, Integer> ReadDB_BOREHOLES_DICTIONARY_LITHOLOGYMap(Statement statement) throws SQLException {
//        HashMap<String, Integer> resultMap = new HashMap<>();
//        String sql = "select * from BOREHOLES_DICTIONARY_LITHOLOGY";
//        ResultSet rs = statement.executeQuery(sql);
//        int i = 1;//标签分类
//        while (rs.next()) {
//            resultMap.put(rs.getString(1) + rs.getString(2), i);
//            i++;
//        }
//        if (rs != null) {   // 关闭记录集
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return resultMap;
//    }
//
//    public static List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> ReadDB_BOREHOLES_DICTIONARY_LITHOLOGY(Statement statement) throws SQLException {
//        List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> BDL = new LinkedList<>();
//        DB_BOREHOLES_DICTIONARY_LITHOLOGY BDLobject = new DB_BOREHOLES_DICTIONARY_LITHOLOGY();
//        String sql = "select * from BOREHOLES_DICTIONARY_LITHOLOGY";
//        ResultSet rs = statement.executeQuery(sql);
//        while (rs.next()) {
//            BDLobject.setBH_TYPE(rs.getString(1));
//            BDLobject.setBH_LITHOLOGY(rs.getString(2));
//            BDLobject.setBH_LITHOLOGY_COLOR_R(rs.getDouble(3));
//            BDLobject.setBH_LITHOLOGY_COLOR_G(rs.getDouble(4));
//            BDLobject.setBH_LITHOLOGY_COLOR_B(rs.getDouble(5));
//            BDLobject.setBH_LITHOLOGY_COLOR_A(rs.getDouble(6));
//            BDL.add(BDLobject);
//            BDLobject = new DB_BOREHOLES_DICTIONARY_LITHOLOGY();
//        }
//        if (rs != null) {   // 关闭记录集
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return BDL;
//    }
//
//    public static List<DB_BOREHOLES_LITHOLOGY> ReadDB_BOREHOLES_LITHOLOGY(Statement statement) throws SQLException {
//        //按照单个钻孔数据分组
//        List<List<DB_BOREHOLES_LITHOLOGY>> BLlist = new LinkedList<>();
//        //单个钻孔数据
//        List<DB_BOREHOLES_LITHOLOGY> BOREHOLES_LITHOLOGYResult = new LinkedList<>();
//        //总共数据量
//        List<DB_BOREHOLES_LITHOLOGY> RR = new LinkedList<>();
//        //临时对象
//        DB_BOREHOLES_LITHOLOGY BL = new DB_BOREHOLES_LITHOLOGY();
//        DB_BOREHOLES_LITHOLOGY BLOld = new DB_BOREHOLES_LITHOLOGY();
//        //copyvalue(BLOld, BL);  //为了第一条数据方便处理
//        BLOld.setBH_ID("Z");//只要不和数据库中任何一条数据名相同即可  其实和第一条不相同即可
//
//        String sql = "select * from BOREHOLES_LITHOLOGY";    //要执行的SQL
//        ResultSet rs = statement.executeQuery(sql);//创建数据对象
//
//        //CREATE TABLE BOREHOLES_LITHOLOGY  (BH_ID  TEXT(50),BH_TYPE  TEXT(50),BH_DEPTH  REAL,BH_THICKNESS  REAL,BH_LITHOLOGY  TEXT(50));
//        //System.out.println("BH_ID" + "\t" + "BH_TYPE" + "\t" + "BH_DEPTH" + "\t" + "BH_THICKNESS" + "\t" + "BH_LITHOLOGY");
//        while (rs.next()) {
////                System.out.print(rs.getString(1) + "\t");
////                System.out.print(rs.getString(2) + "\t");
////                System.out.print(rs.getDouble(3) + "\t");
////                System.out.print(rs.getDouble(4) + "\t");
////                System.out.print(rs.getString(5) + "\t");
////                System.out.println();
//            BL.setBH_ID(rs.getString(1));
//            BL.setBH_TYPE(rs.getString(2));
//            if (rs.getDouble(3) > MaxDepth) {
//                MaxDepth = rs.getDouble(3);//最大钻孔深度
//            }
//            BL.setBH_DEPTH(rs.getDouble(3));
//            BL.setBH_THICKNESS(rs.getDouble(4));
//            BL.setBH_LITHOLOGY(rs.getString(5));
//            //区别钻孔
//            if (!BLOld.getBH_ID().equals(BL.getBH_ID())) {
//                BLlist.add(BOREHOLES_LITHOLOGYResult);
//                BOREHOLES_LITHOLOGYResult = new LinkedList<>();
//            }
//            //BL.setBH_Mid((BLOld.getBH_DEPTH()+BL.getBH_DEPTH())/2);  先不计算中间点
//            copyvalue(BLOld, BL);
//            BOREHOLES_LITHOLOGYResult.add(BL);
//            RR.add(BL);
//            BL = new DB_BOREHOLES_LITHOLOGY();
//        }
//
//        //select count(*) from BOREHOLES_LITHOLOGY group by BH_ID   每一类数据有多少条
//        // select count(*) from (select distinct BH_ID from BOREHOLES_LITHOLOGY) 不同类别有多少条数据
//        //System.out.println("总共有" + BLlist.size() + "类钻孔数据");  //35
//        //System.out.println("总共有" + RR.size() + "条数据");  //552
//        //System.out.println("钻孔最大深度" + MaxDepth + "m");  //164.7
//        if (rs != null) {   // 关闭记录集
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return RR;
//    }
//
//    //生成原始钻孔组合数据  仅有XYZ        //为了相对精确控制点   加入0.0和节制点  已经中间各个点属性
//    public static List<DB_point> GetDBPoint(List<DB_BOREHOLES_POSITION> DBP, List<DB_BOREHOLES_LITHOLOGY> DBL) {
//        List<DB_point> result = new LinkedList<>();
//        DB_point DBPobject = new DB_point();
//        for (int i = 0; i < DBP.size(); i++) {
//            for (int j = 0; j < DBL.size(); j++) {
//                if (DBL.get(j).getBH_ID().equals(DBP.get(i).getBH_ID())) {
//                    double start = DBL.get(j).getBH_DEPTH() - DBL.get(j).getBH_THICKNESS();
//                    double end = DBL.get(j).getBH_DEPTH();
//                    for (double k = start; k <= end; k += 1) {
//                        DBPobject.setBH_X(DBP.get(i).getBH_X());
//                        DBPobject.setBH_Y(DBP.get(i).getBH_Y());
//                        DBPobject.setBH_Z(k);  //注意这里的Z不是标高 代表的是深度值
//                        result.add(DBPobject);
//                        DBPobject = new DB_point();
//                    }
//                }
//            }
//        }
//        System.out.println("总共有" + result.size() + "条数据");
//        return result;
//    }
//
//    //生成原始钻孔组合数据  以XYZ作为key 类作为value存储        //为了相对精确控制点   加入0.0和节制点  已经中间各个点属性
//    public static HashMap<DB_point, Integer> GetDBPointMap(List<DB_BOREHOLES_POSITION> DBP, List<DB_BOREHOLES_LITHOLOGY> DBL, HashMap<String, Integer> Kmap) {
//        HashMap<DB_point, Integer> resultMap = new HashMap<>();
//        DB_point DBPobject = new DB_point();
//        for (int i = 0; i < DBP.size(); i++) {
//            for (int j = 0; j < DBL.size(); j++) {
//                if (DBL.get(j).getBH_ID().equals(DBP.get(i).getBH_ID())) {
//                    double start = DBL.get(j).getBH_DEPTH() - DBL.get(j).getBH_THICKNESS();
//                    double end = DBL.get(j).getBH_DEPTH();
//                    for (double k = start; k <= end; k += 1) {
//                        DBPobject.setBH_X(DBP.get(i).getBH_X());
//                        DBPobject.setBH_Y(DBP.get(i).getBH_Y());
//                        DBPobject.setBH_Z(k);  //注意这里的Z不是标高 代表的是深度值
//                        resultMap.put(DBPobject, Kmap.get(DBL.get(j).getBH_TYPE() + DBL.get(j).getBH_LITHOLOGY()));
//                        DBPobject = new DB_point();
//                    }
//                }
//            }
//        }
//        return resultMap;
//    }
//
//    public static List<DB_BOREHOLES_POSITION> ReadDB_BOREHOLES_POSITION(Statement statement) throws SQLException {
//        List<DB_BOREHOLES_POSITION> result = new LinkedList<>();
//        DB_BOREHOLES_POSITION DBP = new DB_BOREHOLES_POSITION();
//
//        String DBBPSql = "select * from BOREHOLES_POSITION";
//        ResultSet DBBPRs = statement.executeQuery(DBBPSql);
//
//        while (DBBPRs.next()) {
//            DBP.setBH_ID(DBBPRs.getString(1));
//            if (DBBPRs.getDouble(2) > MaxX) {
//                MaxX = DBBPRs.getDouble(2);
//            }
//            if (DBBPRs.getDouble(2) < MinX) {
//                MinX = DBBPRs.getDouble(2);
//            }
//            DBP.setBH_X(DBBPRs.getDouble(2));
//            if (DBBPRs.getDouble(3) > MaxY) {
//                MaxY = DBBPRs.getDouble(3);
//            }
//            if (DBBPRs.getDouble(3) < MinY) {
//                MinY = DBBPRs.getDouble(3);
//            }
//            DBP.setBH_Y(DBBPRs.getDouble(3));
//            if (DBBPRs.getDouble(4) > MaxZ) {
//                MaxZ = DBBPRs.getDouble(4);
//            }
//            if (DBBPRs.getDouble(4) < MinZ) {
//                MinZ = DBBPRs.getDouble(4);
//            }
//            DBP.setBH_Z(DBBPRs.getDouble(4));
//            //System.out.println(DBP.toString());
//            result.add(DBP);
//            DBP = new DB_BOREHOLES_POSITION();
//        }
//
////        System.out.println("DB_BOREHOLES_POSITIONMaxX: " + MaxX + " ");
////        System.out.println("DB_BOREHOLES_POSITIONMaxY: " + MaxY + " ");
////        System.out.println("DB_BOREHOLES_POSITIONMaxZ: " + MaxZ + " ");
////        System.out.println("DB_BOREHOLES_POSITIONMinX: " + MinX + " ");
////        System.out.println("DB_BOREHOLES_POSITIONMinY: " + MinY + " ");
////        System.out.println("DB_BOREHOLES_POSITIONMinZ：" + MinZ + " ");
//
//        if (DBBPRs != null) {   // 关闭记录集
//            try {
//                DBBPRs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//
//
//        final String DRIVER = "org.sqlite.JDBC";
//        //    private static final String URL = "jdbc:sqlite:"+ "D"+ File.separator + "devs"+ File.separator + "data" + File.separator
////            + "DEMO" + File.separator + "QV3dDataTIN" + File.separator + "Project.db";
////    private static final String URL2=" jdbc:sqlite:D:/devs/data/DEMO/QV3dDataTIN/Project.db";
//        final String URL3 = "jdbc:sqlite:." + File.separator + "data" + File.separator + "sample.db";
//
//        //调用Class.forName()方法加载驱动程序
//        try {
//            Class.forName(DRIVER);
//            // System.out.println("成功加载sqlite驱动！");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Connection conn = DriverManager.getConnection(URL3, "", "");
//            Statement statement = conn.createStatement();
//            // System.out.println("成功连接到数据库！");
//
//            List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> DBDLResult = ReadDB_BOREHOLES_DICTIONARY_LITHOLOGY(statement);
//            List<DB_BOREHOLES_LITHOLOGY> DBL = ReadDB_BOREHOLES_LITHOLOGY(statement);
//            List<DB_BOREHOLES_POSITION> DBP = ReadDB_BOREHOLES_POSITION(statement);
//
//            List<DB_point> OriginData = GetDBPoint(DBP, DBL);
//            PrintWriter writer2 = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainDataOrigin.txt"), true));
//            for (int i = 0; i < OriginData.size(); i++) {
//                writer2.append(OriginData.get(i).getBH_X() + " " + OriginData.get(i).getBH_Y() + " " + OriginData.get(i).getBH_Z());
//                writer2.println();
//            }
//            writer2.close();
//
//
//            //生成标签类resultMap
//            HashMap<String, Integer> resultMap = ReadDB_BOREHOLES_DICTIONARY_LITHOLOGYMap(statement);
//            //生成坐标点和类别之间的label
//            HashMap<DB_point, Integer> resultMap2 = GetDBPointMap(DBP, DBL, resultMap);
//            //写成libsvm的格式
//            PrintWriter writer = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainData.txt"), true));
//            for (Map.Entry<DB_point, Integer> entry : resultMap2.entrySet()) {
//                writer.append(Integer.toString(entry.getValue()) + " " + "1:" + entry.getKey().getBH_X() + " " + "2:" + entry.getKey().getBH_Y() + " " + "3:" + entry.getKey().getBH_Z());
//                writer.println();
//            }
//            writer.close();
//
//            //写成libsvm的格式
//            PrintWriter writer3 = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainDataRR.txt"), true));
//            for (Map.Entry<DB_point, Integer> entry : resultMap2.entrySet()) {
//                writer3.append(Integer.toString(entry.getValue()) + " " + entry.getKey().getBH_X() + " " + entry.getKey().getBH_Y() + " " + entry.getKey().getBH_Z());
//                writer3.println();
//            }
//            writer3.close();
//
//
//            if (statement != null) {   // 关闭声明
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (conn != null) {  // 关闭连接对象
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //生成随机数据
//            List<DB_point> RandomData = GetRandomValue(MaxX, MaxY, MaxZ, MinX, MinY, MinZ, MaxDepth);
//            System.out.println("总共随机生成" + readTxtFileIntoStringArrList("D:\\devs\\studies\\scala\\HelloWorld\\TestData.txt") + "数据");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
