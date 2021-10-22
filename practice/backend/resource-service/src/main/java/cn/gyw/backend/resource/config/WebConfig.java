package cn.gyw.backend.resource.config;

import cn.gyw.components.web.config.AbstractMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableWebMvc
@Configuration
public class WebConfig extends AbstractMvcConfig {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:swagger-ui.html");
    }
}
