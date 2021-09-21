package cn.gyw.springboot.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
// 配置包扫描
@ComponentScan(basePackages = { "cn.gyw.springboot" },
        // 是否开启对@Component，@Repository，@Service，@Controller的类进行检测
        useDefaultFilters = true,
        // 过滤
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
public class RootConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("i18n/message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setFallbackToSystemLocale(false);
        return resourceBundleMessageSource;
    }

}
