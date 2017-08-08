package gtl.sqlite.java.example;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.*;
import java.sql.*;
import java.util.*;


public class ReadData2 {

    //钻孔基础数据指标----划分随机数据使用
    private static double MaxX = Double.MIN_VALUE;
    private static double MaxY = Double.MIN_VALUE;
    private static double MaxZ = Double.MIN_VALUE;
    private static double MinX = Double.MAX_VALUE;
    private static double MinY = Double.MAX_VALUE;
    private static double MinZ = Double.MAX_VALUE;
    private static double MaxDepth = Double.MIN_VALUE;
    private static double MinDepth = Double.MAX_VALUE;

    public static Long readTxtFileIntoStringArrList(String filePath) {
        Long a = 0L;
        try {
            String encoding = "UTF-8";
            gtl.io.File file = new gtl.io.File(filePath);
            if (file.isFile() && file.exists()) {     //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);     //考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    a++;
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

        return a;
    }

    /**
     * 生成网格数据
     * 栅格大小1m   先将公有的层先计算出来    下面的44-164 尤其是110-160之间的数据先不要管
     * 先不考虑标高  直接全部设置成0   原始点(x,y,0.0)
     */
    public static List<DB_BOREHOLES_POSITION> GetRandomValue(double MaxXP, double MaxYP, double MaxZP, double MinXP, double MinYP, double MinZP, double MaxDepthP) throws InterruptedException, IOException {
        System.out.println("DB_BOREHOLES_POSITIONMaxX: " + MaxXP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMaxY: " + MaxYP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMaxZ: " + MaxZP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMinX: " + MinXP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMinY: " + MinYP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMinZ：" + MinZP + " ");
        System.out.println("DB_BOREHOLES_POSITIONMinDepth：" + MaxDepthP + " ");

        DB_BOREHOLES_POSITION DBP = new DB_BOREHOLES_POSITION();
        List<DB_BOREHOLES_POSITION> result = new LinkedList<>();

        File writename = new File("TestData.txt"); // 相对路径，如果没有则要建立一个新的TestData.txt文件
        writename.createNewFile(); //创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));

        /**
         * 计数器
         * 栅格大小
         * */
        int ii = 0;
        int jj = 0;
        int kk = 0;
        for (double i = MinXP; i <= MaxXP; i += 1, ii++) {
            for (double j = MinYP; j < MaxYP; j += 1, jj++) {
                for (double k = 0; k < MaxDepthP; k += 1, kk++) {
                    DBP.setBH_X(i);
                    DBP.setBH_Y(j);
                    DBP.setBH_Z(k);
//                    System.out.println(DBP.toString());
//                    TimeUnit.SECONDS.sleep(3);
                    result.add(DBP);
//                    Random random = new Random();
//                    int s = random.nextInt(max) % (max - min + 1) + min;
                    //writer.append(Integer.toString(s) + " " + "1:" + Double.toString(i) + " " + "2:" + Double.toString(j) + " " + "3:" + Double.toString(k));
//                    writer.append(Integer.toString(s) + " "+ Double.toString(i) + " " + Double.toString(j) + " " + Double.toString(k));
//                    writer.println();

                    out.write(Double.toString(i) + " " + Double.toString(j) + " " + Double.toString(k)); // \r\n即为换行
                    out.write("\n");
                    out.flush(); // 把缓存区内容压入文件

                    DBP = new DB_BOREHOLES_POSITION();
                }
            }
        }
        //最后记得关闭文件
        //writer.close();
        out.close();
        System.out.println("iicount:" + ii + "  jjcount:" + (jj / ii) + "  kkcount:" + (kk / jj));
        System.out.println("随机生成" + result.size() + "条数据");
        return result;
    }

    //对象之间赋值操作  要是想要通用就参考链接即可  http://bijian1013.iteye.com/blog/2041392
    public static void copyvalue(DB_BOREHOLES_LITHOLOGY v1, DB_BOREHOLES_LITHOLOGY v2) {
        v1.setBH_ID(v2.getBH_ID());
        v1.setBH_Mid(v2.getBH_Mid());
        v1.setBH_LITHOLOGY(v2.getBH_LITHOLOGY());
        v1.setBH_THICKNESS(v2.getBH_THICKNESS());
        v1.setBH_DEPTH(v2.getBH_DEPTH());
        v1.setBH_TYPE(v2.getBH_TYPE());
    }

    public static void copyvalue2(DB_BOREHOLES_POSITION v1, DB_BOREHOLES_POSITION v2) {
        v1.setBH_ID(v2.getBH_ID());
        v1.setBH_X(v2.getBH_X());
        v1.setBH_Y(v2.getBH_Y());
        v1.setBH_Z(v2.getBH_Z());
    }

    /**
     * 计算点集1中每一个点与点集2中每个点的距离  不能同时操作两个是RDD
     *
     * @param RandomData
     * @param OriginData
     * @return 返回距离矩阵
     */
    public static JavaRDD<DB_pointlabel> calculateDistances(JavaRDD<DB_point> RandomData, HashMap<DB_point, Integer> OriginData) {
        int pcs = RandomData.collect().size();
        System.out.println("number of pcs:" + pcs);
        JavaRDD<DB_pointlabel> drdd = RandomData.map((DB_point t) -> {
            double temp = Double.MAX_VALUE;
            double result;
            DB_pointlabel pl = new DB_pointlabel();
            for (Map.Entry<DB_point, Integer> entry : OriginData.entrySet()) {
                //if ((t.getBH_Z() >= entry.getKey().getBH_Z()) && (t.getBH_Z() <= entry.getKey().getBH_Z() + 1)) {
                result = Math.sqrt(Math.pow(t.getBH_X() - entry.getKey().getBH_X(), 2) + Math.pow(t.getBH_Y() - entry.getKey().getBH_Y(), 2) + Math.pow(t.getBH_X() - entry.getKey().getBH_X(), 2));
                if (temp > result) {
                    temp = result;
                    pl.setBH_X(entry.getKey().getBH_X());
                    pl.setBH_Y(entry.getKey().getBH_Y());
                    pl.setBH_Z(entry.getKey().getBH_Z());
                    pl.setBH_L(entry.getValue());
                }
            }
            //}
            return new DB_pointlabel(pl.getBH_X(), pl.getBH_Y(), pl.getBH_Z(), pl.getBH_L());
        });
        return drdd;
    }


    public static HashMap<DB_point, Integer> GetDistance(List<DB_point> RandomData, HashMap<DB_point, Integer> OriginData) {
        HashMap<DB_point, Integer> resultList = new HashMap<>();
        double temp = Double.MAX_VALUE;
        double result;
        DB_pointlabel pl = new DB_pointlabel();
        for (int i = 0; i < RandomData.size(); i++) {
            for (Map.Entry<DB_point, Integer> entry : OriginData.entrySet()) {
                if ((RandomData.get(i).getBH_Z() >= entry.getKey().getBH_Z()) && (RandomData.get(i).getBH_Z() <= entry.getKey().getBH_Z() + 1)) {
                    result = Math.sqrt(Math.pow(RandomData.get(i).getBH_X() - entry.getKey().getBH_X(), 2) + Math.pow(RandomData.get(i).getBH_Y() - entry.getKey().getBH_Y(), 2) + Math.pow(RandomData.get(i).getBH_X() - entry.getKey().getBH_X(), 2));
                    if (temp > result) {
                        temp = result;
                        pl.setBH_X(entry.getKey().getBH_X());
                        pl.setBH_Y(entry.getKey().getBH_Y());
                        pl.setBH_Z(entry.getKey().getBH_Z());
                        pl.setBH_L(entry.getValue());
                    }
                }
            }
            resultList.put(new DB_point(pl.getBH_X(), pl.getBH_Y(), pl.getBH_Z()), pl.getBH_L());
            pl = new DB_pointlabel();
        }
        System.out.println("resultList的大小为" + resultList.size());
        return resultList;
    }


    /**
     * 求Map<K,V>中Key(键)的最小值对应的key
     *
     * @param map
     * @return
     */
    public static String getMinKey(Map<String, Double> map) {
        if (map == null) return "";
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(list, (Comparator<Map.Entry<String, Double>>) (o1, o2) -> {
            //升序排列
            if ((o1.getValue() - o2.getValue()) > 0) {
                return 1;
            } else if ((o1.getValue() - o2.getValue()) < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        return list.get(0).getKey();
    }


    /**
     * 功能：
     * 计算某点到钻孔垂线的距离计算为L1
     * 计算某点到单段距离计算为L2
     * 钻孔单段的长度为L3
     * 公式为 F=aL1+bL2+C/L3  结果取最小值即可，将单点的属性值设置成最小值
     * <p>
     * 参数：
     *
     * @param A            待插值空间点
     * @param B            所有钻孔数据段每个段的数据
     * @param TriangleList 带插值点周围的每个三个钻孔
     *                     <p>
     *                     结果：
     *                     每个插值点和其对应的属性值
     *                     <p>
     *                     https://www.programcreek.com/java-api-examples/index.php?class=org.apache.spark.api.java.JavaPairRDD&method=reduceByKey
     *                     <p>
     *                     可以将类别对应的结果和序列号组成一个新类 这样就没有必要使用两个HashMap
     */
    public static JavaPairRDD<DB_BOREHOLES_POSITION, Integer> GetDistance(JavaRDD<DB_BOREHOLES_POSITION> A, HashMap<String, List<DB_BOREHOLES_LITHOLOGY>> B, List<DB_ThreePoint> TriangleList, HashMap<String, Double> species, HashMap<String, Integer> kind) {
        JavaPairRDD<DB_BOREHOLES_POSITION, Integer> result;
        STriangulation TT = new STriangulation();
        result = A.mapToPair((DB_BOREHOLES_POSITION t) -> {
            //获取三个钻孔对应的数据信息
            List<DB_BOREHOLES_LITHOLOGY> DBLA = new LinkedList<>();
            List<DB_BOREHOLES_LITHOLOGY> DBLB = new LinkedList<>();
            List<DB_BOREHOLES_LITHOLOGY> DBLC = new LinkedList<>();
            DB_ThreePoint DBTResult = new DB_ThreePoint();
            DBTResult = TT.PisInListTriangle(t, TriangleList);
            for (Map.Entry<String, List<DB_BOREHOLES_LITHOLOGY>> entry : B.entrySet()) {
                if (entry.getKey().equals(DBTResult.getA().getBH_ID())) {
                    DBLA = entry.getValue();
                } else if (entry.getKey().equals(DBTResult.getB().getBH_ID())) {
                    DBLB = entry.getValue();
                } else if (entry.getKey().equals(DBTResult.getC().getBH_ID())) {
                    DBLC = entry.getValue();
                }
            }

            /*
             * 计算点到每个钻孔对应段的信息
             * 单个计算点受到所有钻孔段影响点之和
             * select count(*) from (select distinct BH_LITHOLOGY from BOREHOLES_LITHOLOGY)   20种属性值
             * **/

            double AL1, BL1, CL1;   //点到钻孔的垂直距离
            double AL2;
            double AL3;
            double AR, BR, CR;
            AL1 = Math.sqrt(Math.pow(t.getBH_X() - DBTResult.getA().getBH_X(), 2) + Math.pow(t.getBH_Y() - DBTResult.getA().getBH_Y(), 2));
            BL1 = Math.sqrt(Math.pow(t.getBH_X() - DBTResult.getB().getBH_X(), 2) + Math.pow(t.getBH_Y() - DBTResult.getB().getBH_Y(), 2));
            CL1 = Math.sqrt(Math.pow(t.getBH_X() - DBTResult.getC().getBH_X(), 2) + Math.pow(t.getBH_Y() - DBTResult.getC().getBH_Y(), 2));

            //计算的结果可以直接放在20种值中 然后取最小值赋值即可
            for (int i = 0; i < DBLA.size(); i++) {
                AR = AL1 + Math.abs(t.getBH_Z() - DBLA.get(i).getBH_Mid()) + 1 / DBLA.get(i).getBH_THICKNESS();
                for (Map.Entry<String, Double> entry : species.entrySet()) {
                    if (entry.getKey().equals(DBLA.get(i).getBH_ID())) {
                        entry.setValue(entry.getValue() + AR);
                        break;
                    }
                }
            }

            for (int i = 0; i < DBLB.size(); i++) {
                BR = BL1 + Math.abs(t.getBH_Z() - DBLB.get(i).getBH_Mid()) + 1 / DBLB.get(i).getBH_THICKNESS();
                for (Map.Entry<String, Double> entry : species.entrySet()) {
                    if (entry.getKey().equals(DBLB.get(i).getBH_ID())) {
                        entry.setValue(entry.getValue() + BR);
                        break;
                    }
                }
            }

            for (int i = 0; i < DBLC.size(); i++) {
                CR = CL1 + Math.abs(t.getBH_Z() - DBLC.get(i).getBH_Mid()) + 1 / DBLC.get(i).getBH_THICKNESS();
                for (Map.Entry<String, Double> entry : species.entrySet()) {
                    if (entry.getKey().equals(DBLC.get(i).getBH_ID())) {
                        entry.setValue(entry.getValue() + CR);
                        break;
                    }
                }
            }

            //取最小值
            Integer IR = 0;
            for (Map.Entry<String, Integer> entry : kind.entrySet()) {
                if (getMinKey(species).equals(entry.getKey())) {
                    IR = entry.getValue();
                    break;
                }
            }

            return new Tuple2<>(t, IR);
        });
        return result;
    }

    //从数据库获取数据

    /**
     * BH_TYPE和BH_LITHOLOGY作为一个分类标准
     */
    public static HashMap<String, Integer> ReadDB_BOREHOLES_DICTIONARY_LITHOLOGYMap(Statement statement) throws SQLException {
        HashMap<String, Integer> resultMap = new HashMap<>();
        String sql = "select * from BOREHOLES_DICTIONARY_LITHOLOGY";
        ResultSet rs = statement.executeQuery(sql);
        int i = 1;//标签分类
        while (rs.next()) {
            resultMap.put(rs.getString(1) + rs.getString(2), i);
            i++;
        }
        if (rs != null) {   // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public static List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> ReadDB_BOREHOLES_DICTIONARY_LITHOLOGY(Statement statement) throws SQLException {
        List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> BDL = new LinkedList<>();
        DB_BOREHOLES_DICTIONARY_LITHOLOGY BDLobject = new DB_BOREHOLES_DICTIONARY_LITHOLOGY();
        String sql = "select * from BOREHOLES_DICTIONARY_LITHOLOGY";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            BDLobject.setBH_TYPE(rs.getString(1));
            BDLobject.setBH_LITHOLOGY(rs.getString(2));
            BDLobject.setBH_LITHOLOGY_COLOR_R(rs.getDouble(3));
            BDLobject.setBH_LITHOLOGY_COLOR_G(rs.getDouble(4));
            BDLobject.setBH_LITHOLOGY_COLOR_B(rs.getDouble(5));
            BDLobject.setBH_LITHOLOGY_COLOR_A(rs.getDouble(6));
            BDL.add(BDLobject);
            BDLobject = new DB_BOREHOLES_DICTIONARY_LITHOLOGY();
        }
        if (rs != null) {   // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return BDL;
    }

    public static List<DB_BOREHOLES_LITHOLOGY> ReadDB_BOREHOLES_LITHOLOGY(Statement statement) throws SQLException {
        //按照单个钻孔数据分组
        List<List<DB_BOREHOLES_LITHOLOGY>> BLlist = new LinkedList<>();
        //单个钻孔数据
        List<DB_BOREHOLES_LITHOLOGY> BOREHOLES_LITHOLOGYResult = new LinkedList<>();
        //总共数据量
        List<DB_BOREHOLES_LITHOLOGY> RR = new LinkedList<>();
        //临时对象
        DB_BOREHOLES_LITHOLOGY BL = new DB_BOREHOLES_LITHOLOGY();
        DB_BOREHOLES_LITHOLOGY BLOld = new DB_BOREHOLES_LITHOLOGY();
        //这样写其实是存在一些弊端    最好还是使用boolean
        // BLOld.setBH_ID("Z");//只要不和数据库中任何一条数据名相同即可  其实和第一条不相同即可
        Boolean issame = false;

        String sql = "select * from BOREHOLES_LITHOLOGY";    //要执行的SQL
        ResultSet rs = statement.executeQuery(sql);//创建数据对象

        //CREATE TABLE BOREHOLES_LITHOLOGY  (BH_ID  TEXT(50),BH_TYPE  TEXT(50),BH_DEPTH  REAL,BH_THICKNESS  REAL,BH_LITHOLOGY  TEXT(50));
        //System.out.println("BH_ID" + "\t" + "BH_TYPE" + "\t" + "BH_DEPTH" + "\t" + "BH_THICKNESS" + "\t" + "BH_LITHOLOGY");
        while (rs.next()) {
//                System.out.print(rs.getString(1) + "\t");
//                System.out.print(rs.getString(2) + "\t");
//                System.out.print(rs.getDouble(3) + "\t");
//                System.out.print(rs.getDouble(4) + "\t");
//                System.out.print(rs.getString(5) + "\t");
//                System.out.println();
            BL.setBH_ID(rs.getString(1));
            BL.setBH_TYPE(rs.getString(2));
            if (rs.getDouble(3) > MaxDepth) {
                MaxDepth = rs.getDouble(3);//最大钻孔深度
            }
            BL.setBH_DEPTH(rs.getDouble(3));
            BL.setBH_THICKNESS(rs.getDouble(4));
            BL.setBH_LITHOLOGY(rs.getString(5));
            //区别钻孔
            if (issame && !BLOld.getBH_ID().equals(BL.getBH_ID())) {
                BLlist.add(BOREHOLES_LITHOLOGYResult);
                BOREHOLES_LITHOLOGYResult = new LinkedList<>();
            }
            issame = true;
            copyvalue(BLOld, BL);
            BOREHOLES_LITHOLOGYResult.add(BL);
            RR.add(BL);
            BL = new DB_BOREHOLES_LITHOLOGY();
        }
        //结尾的时候是需要把最后一个种类的数据也放进去的 不然最后一个钻孔的类别是不会的有的
        BLlist.add(BOREHOLES_LITHOLOGYResult);

        //select count(*) from BOREHOLES_LITHOLOGY group by BH_ID   每一类数据有多少条
        // select count(*) from (select distinct BH_ID from BOREHOLES_LITHOLOGY) 不同类别有多少条数据
        //System.out.println("总共有" + BLlist.size() + "类钻孔数据");  //35
        //System.out.println("总共有" + RR.size() + "条数据");  //552
        //System.out.println("钻孔最大深度" + MaxDepth + "m");  //164.7
        if (rs != null) {   // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return RR;
    }


    /**
     * 获取BOREHOLES_LITHOLOGY中BL_LITHOLOGY种类
     */

    public static Set<String> ReadBH_LITHOLOGY(Statement statement) throws SQLException {
        Set<String> SResult = new LinkedHashSet<>();

        String sql = "select * from BOREHOLES_LITHOLOGY";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            SResult.add(rs.getString(5));
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return SResult;
    }

    //钻孔名称对应单个list
    public static HashMap<String, List<DB_BOREHOLES_LITHOLOGY>> ReadDB_BOREHOLES_LITHOLOGYMid(Statement statement) throws SQLException {
        HashMap<String, List<DB_BOREHOLES_LITHOLOGY>> HIDBL = new HashMap<>();
        //按照单个钻孔数据分组
        List<List<DB_BOREHOLES_LITHOLOGY>> BLlist = new LinkedList<>();
        //单个钻孔数据
        List<DB_BOREHOLES_LITHOLOGY> BOREHOLES_LITHOLOGYResult = new LinkedList<>();
        //总共数据量
        List<DB_BOREHOLES_LITHOLOGY> RR = new LinkedList<>();
        //临时对象
        DB_BOREHOLES_LITHOLOGY BL = new DB_BOREHOLES_LITHOLOGY();
        DB_BOREHOLES_LITHOLOGY BLOld = new DB_BOREHOLES_LITHOLOGY();
        Boolean issame = false;

        String sql = "select * from BOREHOLES_LITHOLOGY";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            BL.setBH_ID(rs.getString(1));
            BL.setBH_TYPE(rs.getString(2));
            //先用最低钻孔深度，走通方法以后再去考虑单点深度超过其周边三根钻孔的深度
            if (rs.getDouble(3) > MaxDepth) {
                MaxDepth = rs.getDouble(3);//最大钻孔深度
            }
            BL.setBH_DEPTH(rs.getDouble(3));
            BL.setBH_THICKNESS(rs.getDouble(4));
            BL.setBH_Mid(rs.getDouble(3) - rs.getDouble(4) / 2);
            BL.setBH_LITHOLOGY(rs.getString(5));
            //区别钻孔
            if (issame && !BLOld.getBH_ID().equals(BL.getBH_ID())) {
                BLlist.add(BOREHOLES_LITHOLOGYResult);
                HIDBL.put(BLOld.getBH_ID(), BOREHOLES_LITHOLOGYResult);
                BOREHOLES_LITHOLOGYResult = new LinkedList<>();
            }
            issame = true;
            copyvalue(BLOld, BL);
            BOREHOLES_LITHOLOGYResult.add(BL);
            RR.add(BL);
            BL = new DB_BOREHOLES_LITHOLOGY();
        }
        //结尾的时候是需要把最后一个种类的数据也放进去的 不然最后一个钻孔的类别是不会的有的
        BLlist.add(BOREHOLES_LITHOLOGYResult);
        HIDBL.put(BLOld.getBH_ID(), BOREHOLES_LITHOLOGYResult);

        //select count(*) from BOREHOLES_LITHOLOGY group by BH_ID   每一类数据有多少条
        // select count(*) from (select distinct BH_ID from BOREHOLES_LITHOLOGY) 不同类别有多少条数据
        System.out.println("总共有" + BLlist.size() + "类钻孔数据");  //35
        System.out.println("HashMap记录有" + HIDBL.size() + "类钻孔数据");
        System.out.println("总共有" + RR.size() + "条数据");  //552
        // System.out.println("钻孔最大深度" + MaxDepth + "m");  //164.7

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return HIDBL;
    }

    //生成原始钻孔组合数据  仅有XYZ        //为了相对精确控制点   加入0.0和节制点  已经中间各个点属性
    public static List<DB_point> GetDBPoint(List<DB_BOREHOLES_POSITION> DBP, List<DB_BOREHOLES_LITHOLOGY> DBL) {
        List<DB_point> result = new LinkedList<>();
        DB_point DBPobject = new DB_point();
        for (int i = 0; i < DBP.size(); i++) {
            for (int j = 0; j < DBL.size(); j++) {
                if (DBL.get(j).getBH_ID().equals(DBP.get(i).getBH_ID())) {
                    double start = DBL.get(j).getBH_DEPTH() - DBL.get(j).getBH_THICKNESS();
                    double end = DBL.get(j).getBH_DEPTH();
                    for (double k = start; k <= end; k += 1) {
                        DBPobject.setBH_X(DBP.get(i).getBH_X());
                        DBPobject.setBH_Y(DBP.get(i).getBH_Y());
                        DBPobject.setBH_Z(k);  //注意这里的Z不是标高 代表的是深度值
                        result.add(DBPobject);
                        DBPobject = new DB_point();
                    }
                }
            }
        }
        System.out.println("总共有" + result.size() + "条数据");
        return result;
    }

    //生成原始钻孔组合数据  以XYZ作为key 类作为value存储        //为了相对精确控制点   加入0.0和节制点  已经中间各个点属性
    public static HashMap<DB_point, Integer> GetDBPointMap(List<DB_BOREHOLES_POSITION> DBP, List<DB_BOREHOLES_LITHOLOGY> DBL, HashMap<String, Integer> Kmap) {
        HashMap<DB_point, Integer> resultMap = new HashMap<>();
        DB_point DBPobject = new DB_point();
        for (int i = 0; i < DBP.size(); i++) {
            for (int j = 0; j < DBL.size(); j++) {
                if (DBL.get(j).getBH_ID().equals(DBP.get(i).getBH_ID())) {
                    double start = DBL.get(j).getBH_DEPTH() - DBL.get(j).getBH_THICKNESS();
                    double end = DBL.get(j).getBH_DEPTH();
                    for (double k = start; k <= end; k += 1) {
                        DBPobject.setBH_X(DBP.get(i).getBH_X());
                        DBPobject.setBH_Y(DBP.get(i).getBH_Y());
                        DBPobject.setBH_Z(k);  //注意这里的Z不是标高 代表的是深度值
                        resultMap.put(DBPobject, Kmap.get(DBL.get(j).getBH_TYPE() + DBL.get(j).getBH_LITHOLOGY()));
                        DBPobject = new DB_point();
                    }
                }
            }
        }
        return resultMap;
    }

    public static List<DB_BOREHOLES_POSITION> ReadDB_BOREHOLES_POSITION(Statement statement) throws SQLException {
        List<DB_BOREHOLES_POSITION> result = new LinkedList<>();
        DB_BOREHOLES_POSITION DBP = new DB_BOREHOLES_POSITION();
        String DBBPSql = "select * from BOREHOLES_POSITION";
        ResultSet DBBPRs = statement.executeQuery(DBBPSql);
        while (DBBPRs.next()) {
            DBP.setBH_ID(DBBPRs.getString(1));
            if (DBBPRs.getDouble(2) > MaxX) {
                MaxX = DBBPRs.getDouble(2);
            }
            if (DBBPRs.getDouble(2) < MinX) {
                MinX = DBBPRs.getDouble(2);
            }
            DBP.setBH_X(DBBPRs.getDouble(2));
            if (DBBPRs.getDouble(3) > MaxY) {
                MaxY = DBBPRs.getDouble(3);
            }
            if (DBBPRs.getDouble(3) < MinY) {
                MinY = DBBPRs.getDouble(3);
            }
            DBP.setBH_Y(DBBPRs.getDouble(3));
            if (DBBPRs.getDouble(4) > MaxZ) {
                MaxZ = DBBPRs.getDouble(4);
            }
            if (DBBPRs.getDouble(4) < MinZ) {
                MinZ = DBBPRs.getDouble(4);
            }
            DBP.setBH_Z(DBBPRs.getDouble(4));
            //System.out.println(DBP.toString());
            result.add(DBP);
            DBP = new DB_BOREHOLES_POSITION();
        }

        if (DBBPRs != null) {   // 关闭记录集
            try {
                DBBPRs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("SVM")
                .getOrCreate();

        JavaSparkContext JSC = new JavaSparkContext(spark.sparkContext());

        final String DRIVER = "org.sqlite.JDBC";
        final String URL3 = "jdbc:sqlite:." + File.separator + "data" + File.separator + "sample.db";

        //调用Class.forName()方法加载驱动程序
        try {
            Class.forName(DRIVER);
            System.out.println("成功加载sqlite驱动！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DriverManager.getConnection(URL3, "", "");
            Statement statement = conn.createStatement();
            System.out.println("成功连接到数据库！");

            List<DB_BOREHOLES_DICTIONARY_LITHOLOGY> DBDLResult = ReadDB_BOREHOLES_DICTIONARY_LITHOLOGY(statement);
            List<DB_BOREHOLES_LITHOLOGY> DBL = ReadDB_BOREHOLES_LITHOLOGY(statement);


            //获取每个钻孔每段的长度以及中间点
            HashMap<String, List<DB_BOREHOLES_LITHOLOGY>> HIDBL = ReadDB_BOREHOLES_LITHOLOGYMid(statement);
//            PrintWriter writerHIDBL = new PrintWriter(new FileOutputStream(new gtl.io.File("HIDBL.txt"), true));
//            Iterator<Map.Entry<String, List<DB_BOREHOLES_LITHOLOGY>>> it = HIDBL.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, List<DB_BOREHOLES_LITHOLOGY>> entry = it.next();
//                String key = entry.getKey();
//                List<DB_BOREHOLES_LITHOLOGY> value = entry.getValue();
//                writerHIDBL.append(key);
//                for (int i = 0; i < value.size(); i++) {
//                    writerHIDBL.append(" " + value.get(i));
//                }
//                writerHIDBL.println();
//            }
//            writerHIDBL.close();


            //获取钻孔平面位置点   以便进行三角剖分 为后面的数据做准备
//            List<DB_BOREHOLES_POSITION> DBP = ReadDB_BOREHOLES_POSITION(statement);
//            PrintWriter WriterDBP = new PrintWriter(new FileOutputStream(new gtl.io.File("DBP.txt"), true));
//            for (int i = 0; i < DBP.size(); i++) {
//                WriterDBP.append(DBP.get(i).getBH_ID() + " " + DBP.get(i).getBH_X() + " " + DBP.get(i).getBH_Y() + " " + DBP.get(i).getBH_Z());
//                WriterDBP.println();
//            }
//            WriterDBP.close();

            //    List<DB_point> OriginData = GetDBPoint(DBP, DBL);
//            PrintWriter writer2 = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainDataOrigin.txt"), true));
//            for (int i = 0; i < OriginData.size(); i++) {
//                writer2.append(OriginData.get(i).getBH_X() + " " + OriginData.get(i).getBH_Y() + " " + OriginData.get(i).getBH_Z());
//                writer2.println();
//            }
//            writer2.close();


            //生成标签类resultMap
            //       HashMap<String, Integer> resultMap = ReadDB_BOREHOLES_DICTIONARY_LITHOLOGYMap(statement);
            //生成坐标点和类别之间的label
            // HashMap<DB_point, Integer> resultMap2 = GetDBPointMap(DBP, DBL, resultMap);
            //写成libsvm的格式
//            PrintWriter writer = new PrintWriter(new FileOutputStream(new gtl.io.File("resultMap2.txt"), true));
//            for (Map.Entry<DB_point, Integer> entry : resultMap2.entrySet()) {
//                writer.append(Integer.toString(entry.getValue()) + " " + entry.getKey().getBH_X() + " " + entry.getKey().getBH_Y() + " " + entry.getKey().getBH_Z());
//                writer.println();
//            }
//            writer.close();

            //写成libsvm的格式
//            PrintWriter writer = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainData.txt"), true));
//            for (Map.Entry<DB_point, Integer> entry : resultMap2.entrySet()) {
//                writer.append(Integer.toString(entry.getValue()) + " " + "1:" + entry.getKey().getBH_X() + " " + "2:" + entry.getKey().getBH_Y() + " " + "3:" + entry.getKey().getBH_Z());
//                writer.println();
//            }
//            writer.close();

            //写成libsvm的格式
//            PrintWriter writer3 = new PrintWriter(new FileOutputStream(new gtl.io.File("TrainDataRR.txt"), true));
//            for (Map.Entry<DB_point, Integer> entry : resultMap2.entrySet()) {
//                writer3.append(Integer.toString(entry.getValue()) + " " + entry.getKey().getBH_X() + " " + entry.getKey().getBH_Y() + " " + entry.getKey().getBH_Z());
//                writer3.println();
//            }
//            writer3.close();


            if (statement != null) {   // 关闭声明
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {  // 关闭连接对象
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            List<DB_ThreePoint> TriangleList = new LinkedList<>();

            //类别和对应序列号
            Set<String> Sresult=ReadBH_LITHOLOGY(statement);
            int count=0;
            HashMap<String, Integer> kind=new HashMap<>();
            for (String str:Sresult) {
                kind.put(str,count);
            }
            //原始值
            HashMap<String, Double> species = new HashMap<>();
            for (String str:Sresult) {
                species.put(str,0.0);
            }
            //生成随机数据
            List<DB_BOREHOLES_POSITION> MyRandomData = GetRandomValue(MaxX, MaxY, MaxZ, MinX, MinY, MinZ, 43.4);
            // System.out.println("总共随机生成" + readTxtFileIntoStringArrList("D:\\devs\\studies\\scala\\HelloWorld\\TestData.txt") + "数据");
            JavaRDD<DB_BOREHOLES_POSITION> A = JSC.parallelize(MyRandomData);
            GetDistance(A, HIDBL, TriangleList, species, kind);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
