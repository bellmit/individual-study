package cn.gyw.spring.mybatis;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Spring 接入Mybatis
 */
@Configuration
@PropertySource(value = {"db/db.properties"})
@ComponentScan(basePackages = { "cn.gyw.spring.mybatis" })
public class MybatisConfig {

	@Bean
	public DataSource dataSource(@Value("${db.local.driverClassName}") String driverClassName,
			@Value("${db.local.url}") String jdbcUrl, @Value("${db.local.username}") String username,
			@Value("${db.local.password}") String password) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	/**
	 * 生成 Mybatis SqlSessionFactory 的 bean
	 * 
	 * @return
	 * @throws IOException 
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		// 使用java 代码配置 Configuration，也可以使用mybatis-config.xml，二者不可同时使用
		sqlSessionFactoryBean.setTypeAliasesPackage("cn.gyw.spring.mybatis.model");
		sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/mysql/*.xml"));
		
		return sqlSessionFactoryBean;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactoryBean sqlSessionFactoryBean) {
		try {
			return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于加载 @Mapper 接口注册到Spring IOC容器中
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("cn.gyw.spring.mybatis.mapper");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		return mapperScannerConfigurer;
	}
}
