package cn.gyw.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @desc
 * @date 2021/12/23 21:46
 */
public class AwareBean implements EnvironmentAware, ApplicationContextAware, BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(">> BeanFactoryAware.setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(">> ApplicationContextAware.setApplicationContext");
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(">> EnvironmentAware.setEnvironment");
    }
}
