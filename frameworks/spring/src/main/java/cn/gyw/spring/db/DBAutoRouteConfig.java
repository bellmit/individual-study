package cn.gyw.spring.db;

import cn.gyw.spring.db.route.DynamicDataSource;
import cn.gyw.spring.db.route.DynamicDataSourceEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 */
@Configuration
@ComponentScan(basePackages = {"cn.gyw.spring.db"})
@PropertySource(value = {"classpath:db/db.properties"})
// @ComponentScan(value = "cn.gyw.spring")
// 开启声明式事务，proxyTargetClass：是否强制使用基于Cglib类代理
@EnableTransactionManagement(proxyTargetClass = false)
public class DBAutoRouteConfig {

    @Value("${db.master.driverClassName}")
    private String masterDriverClassName;
    @Value("${db.master.url}")
    String masterUrl;
    @Value("${db.master.username}")
    String masterUserName;
    @Value("${db.master.password}")
    String masterPassword;

    @Value("${db.slaver.driverClassName}")
    String slaverDriverClassName;
    @Value("${db.slaver.url}")
    String slaverUrl;
    @Value("${db.slaver.username}")
    String slaverUserName;
    @Value("${db.slaver.password}")
    String slaverPassword;

    /**
     * 动态数据源
     * 数据源自动路由
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        DataSource master = createDataSource(masterDriverClassName, masterUrl, masterUserName, masterPassword);
        DataSource slaver = createDataSource(slaverDriverClassName, slaverUrl, slaverUserName, slaverPassword);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDynamicDataSourceEntry(new DynamicDataSourceEntry());

        dynamicDataSource.setDefaultTargetDataSource(master);

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.MASTER, master);
        dataSourceMap.put(DataSourceType.SLAVER, slaver);

        dynamicDataSource.setTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }

    // 内部调用
    private DataSource createDataSource(String driver, String url, String user, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * 事务管理工厂
     *
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource) {
        /**
         * 没有提供池化连接的机制；每次调用 getConnectionO方法获取新连接时，只是简单地创建一个新的连接。
         * 因此，这个数据源类比较适合在单元测试或简单的独立应用中使用
         */
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    /**
     * spring jdbc template
     *
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
