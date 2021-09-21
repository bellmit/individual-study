package cn.gyw.platform.configuration.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.gyw.platform.configuration.interfaces.IConfiguration;

/**
 *
 */
@Component
public class ConfigurationServiceLocator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurationServiceLocator.applicationContext = applicationContext;
    }

    public static IConfiguration getInstance() {
        return applicationContext.getBean(IConfiguration.class);
    }
}
