package cn.gyw.backend.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement
@MapperScan({"cn.gyw.backend.system.mapper", "cn.gyw.backend.system.dao"})
public class MybatisConfig {
}
