package cn.gyw.backend.resource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @description 数据库配置
 * @createdTime 2021/10/22 16:16
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {CrawlerResourceConfig.PACKAGE_HOUSE_INFO}, sqlSessionFactoryRef = "crawlerSqlSessionFactory")
public class CrawlerResourceConfig {

    public static final String PACKAGE_HOUSE_INFO = "cn.gyw.backend.resource.houseinfo";
    public static final String MAPPER_LOCATION = "classpath:mapper/crawler/*.xml";

    @Value("${crawler.ds.driverClassName}")
    private String crawlerDriverClass;
    @Value("${crawler.ds.url}")
    private String crawlerUrl;
    @Value("${crawler.ds.userName}")
    private String crawlerUserName;
    @Value("${crawler.ds.password}")
    private String crawlerPassword;

    @Bean(name = "crawlerDataSource")
    @Primary
    public DataSource crawlerDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(crawlerDriverClass);
        dataSource.setJdbcUrl(crawlerUrl);
        dataSource.setUsername(crawlerUserName);
        dataSource.setPassword(crawlerPassword);
        return dataSource;
    }

    @Bean(name = "crawlerTransactionManager")
    @Primary
    public DataSourceTransactionManager crawlerTransactionManager() {
        return new DataSourceTransactionManager(crawlerDataSource());
    }

    @Bean(name = "crawlerSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("crawlerDataSource") DataSource crawlerDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(crawlerDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
