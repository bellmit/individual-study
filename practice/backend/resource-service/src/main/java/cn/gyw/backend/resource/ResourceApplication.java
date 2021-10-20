package cn.gyw.backend.resource;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @description 资源服务
 * @createdTime 2021/10/20 15:00
 */
@SpringBootApplication(scanBasePackages = {"cn.gyw.backend", "cn.gyw.components", "cn.gyw.platform"},
        exclude = {FreeMarkerAutoConfiguration.class})
public class ResourceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().main(ResourceApplication.class)
                .bannerMode(Banner.Mode.OFF).properties("config.properties").run(args);
    }
}
