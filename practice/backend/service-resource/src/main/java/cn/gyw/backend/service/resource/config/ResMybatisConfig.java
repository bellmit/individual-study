package cn.gyw.backend.service.resource.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @desc
 * @createdTime 2022/1/16 15:25
 */
@Slf4j
@Configuration
// basePackages 所有的接口，都会创建mapper代理，导致*Service也被代理，数量少可以使用@Mapper注解代替@MapperScan
@MapperScan(basePackages = {"cn.gyw.backend.service.resource.dao"}, sqlSessionFactoryRef = "resSqlSessionFactory")
public class ResMybatisConfig {

    public static final String MAPPER_LOCATION = "classpath:mapper/res/*.xml";

    @Value("${ds.res.url}")
    private String url;
    @Value("${ds.res.username}")
    private String user;
    @Value("${ds.res.driverClassName}")
    private String driverClass;

    @Bean(name = "resDataSource")
    @Profile(value = "local")
    public DataSource localDataSource(@Value("${ds.res.host}") String host,
                                      @Value("${ds.res.password}") String password) {
        return createDataSource(host, password);
    }

    @Bean(name = "resDataSource")
    @Profile(value = "remote")
    public DataSource remoteDataSource(@Value("${ds.remote.res.host}") String host,
                                       @Value("${ds.remote.res.password}") String password) {
        return createDataSource(host, password);
    }

    @Bean(name = "resTransactionManager")
    public DataSourceTransactionManager resTransactionManager(@Qualifier("resDataSource") DataSource resDataSource) {
        return new DataSourceTransactionManager(resDataSource);
    }

    @Bean(name = "resSqlSessionFactory")
    public SqlSessionFactory resSqlSessionFactory(@Qualifier("resDataSource") DataSource resDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(resDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    /**
     * 会话管理
     * <p>
     * TkMybatis 多数据源需要配置，否则异常
     *
     * @param sqlSessionFactory 会话连接工厂
     * @return 会话模板
     */
    @Bean
    public SqlSessionTemplate resSqlSessionTemplate(@Qualifier("resSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private DataSource createDataSource(String host, String password) {
        log.info("连接数据库地址：{}", host);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(String.format(url, host));
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
