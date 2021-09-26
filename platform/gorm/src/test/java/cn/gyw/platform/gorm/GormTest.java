package cn.gyw.platform.gorm;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

public class GormTest {

    private DataSource dataSource;

    @Test
    public void personDaoTest() {
        PersonDao personDao = new PersonDao();
        personDao.setDataSource(dataSource);

        personDao.selectAll().forEach(System.out::println);
    }


    @BeforeEach
    public void init() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/demo_2021" +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false" +
                "&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        this.dataSource = druidDataSource;
    }
}
