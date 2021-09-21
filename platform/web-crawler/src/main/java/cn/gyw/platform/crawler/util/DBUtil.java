package cn.gyw.platform.crawler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据采集
 */
public class DBUtil {

    public static Connection getConnection() {
        //四要素
        String driver = "com.mysql.jdbc.Driver";
        String url =  "jdbc:mysql://127.0.0.1:3306/lucene_data?ssl=false";
        String username = "root";
        String password = "root";
        try {
            //获取连接并返回
            Class.forName(driver);
            return DriverManager.getConnection(url,  username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
