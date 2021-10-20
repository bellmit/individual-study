package cn.gyw.backend.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

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
}
