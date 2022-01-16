package cn.gyw.backend.service.system.config;

import com.alibaba.druid.pool.DruidDataSource;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"cn.gyw.backend.service.system.mapper", "cn.gyw.backend.service.system.dao"},
        sqlSessionFactoryRef = "systemSqlSessionFactory")
public class SystemMybatisConfig {

    static final String MAPPER_LOCATION = "classpath:mapper/sys/*.xml";

    @Value("${ds.system.host}")
    private String host;
    @Value("${ds.system.url}")
    private String url;
    @Value("${ds.system.username}")
    private String user;
    @Value("${ds.system.password}")
    private String password;
    @Value("${ds.system.driverClassName}")
    private String driverClass;

    @Bean(name = "systemDataSource")
    @Profile(value = "local")
    public DataSource systemDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(String.format(url, host));
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "systemDataSource")
    @Profile(value = "remote")
    public DataSource remoteDataSource(@Value("${ds.remote.system.host}") String remoteHost,
                                       @Value("${ds.remote.system.password}") String remotePwd) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(String.format(url, remoteHost));
        dataSource.setUsername(user);
        dataSource.setPassword(remotePwd);
        return dataSource;
    }

    /**
     * 事务管理工厂
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "systemTransactionManager")
    public PlatformTransactionManager systemTransactionManager(@Qualifier("systemDataSource") DataSource dataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    @Bean(name = "systemSqlSessionFactory")
    public SqlSessionFactory systemSqlSessionFactory(@Qualifier("systemDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SystemMybatisConfig.MAPPER_LOCATION));
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
    public SqlSessionTemplate systemSqlSessionTemplate(@Qualifier("systemSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
