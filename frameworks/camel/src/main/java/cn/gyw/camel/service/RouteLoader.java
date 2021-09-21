package cn.gyw.camel.service;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Spring 容器启动后，执行
 */
@Service
public class RouteLoader implements ApplicationRunner, ApplicationContextAware {

    @Autowired
    private CamelContext camelContext;

    private ApplicationContext sprintContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        camelContext.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.sprintContext = applicationContext;
    }
}
