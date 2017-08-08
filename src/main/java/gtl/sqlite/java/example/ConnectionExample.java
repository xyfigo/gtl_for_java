package gtl.sqlite.java.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionExample {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String URL = "jdbc:sqlite:." + File.separator + "data" + File.separator + "sample.db";

    public static void main(String[] args) {

        try {
            Class.forName(DRIVER);  //加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(URL, "", "");
            Statement statement = conn.createStatement();
            //statement.executeUpdate("create table test(name varchar(10))");
            statement.executeUpdate("drop table test");
            statement.close();          //先开后关闭，可以只关闭connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
