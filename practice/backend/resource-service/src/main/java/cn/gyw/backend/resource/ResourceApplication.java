package cn.gyw.backend.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 资源服务
 *
 * @date 2021/10/20 15:00
 */
@SpringBootApplication(scanBasePackages = {"cn.gyw.backend", "cn.gyw.components", "cn.gyw.platform"},
        exclude = {FreeMarkerAutoConfiguration.class})
public class ResourceApplication {

    private static final Logger log = LoggerFactory.getLogger(ResourceApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ResourceApplication.class, args);

        if (context.containsBean("scheduleConfig")) {
            System.out.println(">>" + context.getBean("scheduleConfig"));
        }
    }

    @Bean
    public ApplicationRunner applicationRunner(@Autowired Environment environment) {
        return args -> {
            String port = environment.getProperty("server.port");
            log.info("Swagger ui:{}", "http://localhost:" + port + "/swagger-ui.html");
        };
    }
}
