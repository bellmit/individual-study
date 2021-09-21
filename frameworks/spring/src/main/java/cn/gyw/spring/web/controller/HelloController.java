package cn.gyw.spring.web.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * spring web test
 */
@RestController
@RequestMapping("/hello")
public class HelloController implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @GetMapping
    public String hello() {
        Arrays.asList(applicationContext.getBeanDefinitionNames()).forEach((beanName) -> {
            System.out.println(">>" + beanName);
        });
        return "say hello";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
