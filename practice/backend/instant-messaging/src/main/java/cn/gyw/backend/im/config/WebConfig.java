package cn.gyw.backend.im.config;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * SpringBoot + javax.websocket 支持
 *
 * 1. 引入 spring-boot-starter-websocket 包
 * 2. 嵌入式Tomcat 需要 ServerEndpointExporter + @Component 修饰的 @ServerEndpoint 类
 * 3. 非嵌入式Tomcat 不需要 2 步骤，由容器负责
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> servletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletRegistrationBean.getUrlMappings().forEach(System.out::println);
        return servletRegistrationBean;
    }

    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // javascript file
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        // css file
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/");
        // 解决Swagger-ui不显示的问题
        registry.addResourceHandler("swagger-ui.htm")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    // 使用嵌入式Tomcat的javax.websocket 必须要注入该bean，否则无法访问
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
