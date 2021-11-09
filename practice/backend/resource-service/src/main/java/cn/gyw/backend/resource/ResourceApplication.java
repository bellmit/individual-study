package cn.gyw.backend.resource;

import cn.gyw.backend.resource.houseinfo.ext.HouseInfoCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger log = LoggerFactory.getLogger(ResourceApplication.class);

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
                                               @Autowired HouseInfoCsvReader reader) {
        return args -> {
            String port = environment.getProperty("server.port");
            log.info("Swagger ui:{}", "http://localhost:" + port + "/swagger-ui.html");
            reader.readAndSaveDB();
        };
    }
}
