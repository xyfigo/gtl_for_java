package gtl.oracle.java.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionExample {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    /*
    格式一:
    Oracle JDBC Thin using an SID:
    jdbc:oracle:thin:@host:port:SID
    Example: jdbc:oracle:thin:@localhost:1521:orcl
    格式二: Oracle JDBC Thin using a ServiceName:
    jdbc:oracle:thin:@//host:port/service_name
    Example:jdbc:oracle:thin:@//localhost:1521/orcl.city.com
    格式三：Oracle JDBC Thin using a TNSName:
    jdbc:oracle:thin:@TNSName
    Example: jdbc:oracle:thin:@TNS_ALIAS_NAME
    */
    private static final String URL = "jdbc:oracle:thin:@//59.71.143.108:1521/pdborcl01";


    public static void main(String[] args) {

        try {
            Class.forName(DRIVER);  //加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, "system", "orcl");
            Statement statement = conn.createStatement();
            statement.executeUpdate("create table test (name varchar2(25))");
            statement.close();          //先开后关闭，可以只关闭connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        OracleConnection conn = null;
        try{
            OracleDriver dr = new OracleDriver();
            Properties prop = new Properties();
            prop.setProperty("user","system");
            prop.setProperty("password","orcl");
            conn = (OracleConnection)dr.connect(URL,prop);
            conn.setAutoCommit(false);
        } catch( SQLException ex ){
            ex.printStackTrace();
        }
        */
    }
}
