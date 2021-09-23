package cn.gyw.backend.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@EnableAspectJAutoProxy
@EnableEurekaClient
@EntityScan("cn.gyw.platform")
@EnableJpaRepositories("cn.gyw.platform")
@SpringBootApplication(scanBasePackages = {"cn.gyw.backend", "cn.gyw.components", "cn.gyw.platform"},
        exclude = {FreeMarkerAutoConfiguration.class})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(@Autowired ApplicationContext context,
                                               @Value("${server.port}") int port) {
        return (arguments) -> {
            System.out.println("http://localhost:"+ port + "/swagger-ui.html");
        };
    }
}
