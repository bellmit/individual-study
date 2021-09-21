package cn.gyw.springboot.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.gyw.springboot.intercept.LogInterceptor;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "cn.gyw.springboot.controller")
public class WebConfig implements WebMvcConfigurer {

    /**
     * Spring Boot<=1.3 无需定义，Spring Boot自动定义
     * Spring Boot >= 1.4 Spring Boot不再自动定义一个RestTemplate，手动通过builder生成
     *
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/jsp/*")
                // swagger 相关接口
                .excludePathPatterns("/swagger*/**", "/webjars/**", "/v2/**");
    }
    
    /**
     * 异步支持
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // tomcat默认10秒
        configurer.setDefaultTimeout(30 * 1000L);
        // 借助的TaskExecutor
        configurer.setTaskExecutor(createTaskExecutor());
    }

    // 线程池
    private ThreadPoolTaskExecutor createTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(120);
        return taskExecutor;
    }
    
    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // 解决Swagger-ui不显示的问题
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
