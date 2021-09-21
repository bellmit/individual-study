package cn.gyw.backend.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"cn.gyw.backend.search.dao"})
public class MybatisConfig {
}
