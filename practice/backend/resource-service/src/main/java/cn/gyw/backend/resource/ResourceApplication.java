package cn.gyw.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @description 资源服务
 * @createdTime 2021/10/20 15:00
 */
@SpringBootApplication(scanBasePackages = {"cn.gyw.backend", "cn.gyw.components", "cn.gyw.platform"},
        exclude = {FreeMarkerAutoConfiguration.class})
public class ResourceApplication {

    public static void main(String[] args) {
        try {
            // new SpringApplicationBuilder().main(ResourceApplication.class)
            //         .bannerMode(Banner.Mode.OFF).properties("config.properties").run(args);
            SpringApplication.run(ResourceApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public ApplicationRunner applicationRunner(@Autowired Environment environment,
                                               @Value("${crawler.ds.host}") String dsUrl,
                                               @Value("${crawler.ds.password}") String pwd) {
        return args -> {
            System.out.println("env.local.name :" + environment.getProperty("env.local.name"));
            System.out.println("dsUrl:" + dsUrl);
            System.out.println("pwd:" + pwd);
        };
    }
}
