package cn.gyw.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring Web 配置
 */
@Configuration
@ComponentScan(value = "cn.gyw.spring.web",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)},
        useDefaultFilters = false
)
@EnableWebMvc // @EnableWebMvc注解会开启一些默认的配置，如一些ViewResolver或者MessageConverter
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 视图解析器
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/static/");
        resolver.setSuffix(".html");
        resolver.setOrder(1);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * 静态资源处理方式一：
     * 不拦截静态资源，由web容器处理
     *
     * <mvc:default-servlet-handler/>
     * @param configurer
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //对静态资源的请求转发到容器缺省的servlet，而不使用DispatcherServlet
        configurer.enable("default");
    }

    /**
     * 添静态资源处理方式二：
     * 允许静态资源存放的地方，由SpringMVC处理静态资源
     *
     * 静态资源访问控制：假如defaultServlet 没有过滤到接收的静态资源是会报404的
     * 配置addResourceHandlers 不能继承WebMvcConfigurationSupport
     * 因为它拥有子类DelegatingWebMvcConfiguration 已经重写了这个方法。
     * 所以运行时，在它方法debug的时候，发现我写的方法一点用都没 注意！！！
     * 下面相当于
     * <mvc:resources mapping="/resources/**"    location="/statics/" />
     * 支持location="classpath:xxxxx"
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
        registry.addResourceHandler("/html/**")
                .addResourceLocations("/static/html/");
        registry.addResourceHandler( "/css/**", "/scripts/**")
                .addResourceLocations("/static/css/", "/static/scripts/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 文件上传处理器
     *
     * 必须指定name = multipartResolver，否则调度器DispatcherServlet 找不到处理器
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        long _10m = 10 * 1024 * 1024;
        // 上传文件的大小，单位：byte
        multipartResolver.setMaxUploadSize(_10m);
        // 上传文件的编码
        multipartResolver.setDefaultEncoding("UTF-8");

        return multipartResolver;
    }
}
