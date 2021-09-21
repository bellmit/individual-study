package cn.gyw.camel;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Camel
 * http://localhost:8088/camel/api-doc
 * 
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = { "cn.gyw" })
public class CamelApplication {

    public static void main(String[] args) {
        // 启动 spring 上下文
        try {
            ConfigurableApplicationContext apc = new SpringApplicationBuilder(CamelApplication.class)
                    .properties("spring.config.name:application").build().run(args);
            // 启动 camel 上下文
//		final CamelSpringBootApplicationController camelContext = apc
//				.getBean(CamelSpringBootApplicationController.class);
//		camelContext.run();
            System.out.println("test rest api by : http://localhost:8088/camel/api");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
