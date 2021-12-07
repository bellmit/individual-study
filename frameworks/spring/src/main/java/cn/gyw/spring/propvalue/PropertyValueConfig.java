package cn.gyw.spring.propvalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 属性字段注入学习
 */
//使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
@PropertySource(value = {"classpath:application.properties"})
@ComponentScan(basePackages = {"cn.gyw.spring.propvalue"})
@Configuration
public class PropertyValueConfig {

    @Bean
    public PropValueBean propValueBean() {
        return new PropValueBean();
    }

}
