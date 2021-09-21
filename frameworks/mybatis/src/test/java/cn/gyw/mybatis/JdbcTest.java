package cn.gyw.mybatis;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

    @Test
    public void testConnect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/community?useSSL=false";
        String username = "root";
        String password = "root";
        Connection conn = null;

        Class.forName("com.mysql.jdbc.Driver");
        //2.获取与数据库的链接
        conn = DriverManager.getConnection(url, username, password);

        System.out.println(conn);
    }
}
