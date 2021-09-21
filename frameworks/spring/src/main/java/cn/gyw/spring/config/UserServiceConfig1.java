package cn.gyw.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 测试父子容器
 *
 * @see UserServiceConfig2
 */
@ComponentScan(basePackages = "cn.gyw.spring.service.user1")
@Configuration
public class UserServiceConfig1 {
}
