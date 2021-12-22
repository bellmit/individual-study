package cn.gyw.middleware.nacos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(value = "cn.gyw.middleware.nacos.controller",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)},
        useDefaultFilters = false
)
public class WebMvcConfig implements WebMvcConfigurer {
}
