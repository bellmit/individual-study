package cn.gyw.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 测试父子容器
 *
 * @see UserServiceConfig1
 */
@ComponentScan(basePackages = "cn.gyw.spring.service.user2")
@Configuration
public class UserServiceConfig2 {
}
