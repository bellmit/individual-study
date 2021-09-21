package cn.gyw.backend.fileserver.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context;
    
    public static ApplicationContext get() {
        return AppContext.context;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.context = applicationContext;
    }

}
