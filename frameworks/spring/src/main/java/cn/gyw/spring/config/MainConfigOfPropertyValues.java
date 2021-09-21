package cn.gyw.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cn.gyw.spring.model.UserSpring;

//使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
@PropertySource(value = {"classpath:application.properties"})
@ComponentScan(basePackages = {"cn.gyw.spring.controller"})
@Configuration
public class MainConfigOfPropertyValues {

    @Bean(initMethod = "init", destroyMethod = "over")
    public UserSpring user() {
        return new UserSpring();
    }

}
