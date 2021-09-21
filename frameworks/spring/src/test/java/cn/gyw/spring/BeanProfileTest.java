package cn.gyw.spring;

import cn.gyw.spring.config.MainConfigOfProfile;
import cn.gyw.spring.model.Book;
import cn.gyw.spring.model.BookStore;
import cn.gyw.spring.web.controller.HelloController;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean 自动装配
 */
public class BeanProfileTest {

    public BeanProfileTest() {
//        super(MainConfigOfProfile.class);
    }

    //1、使用命令行动态参数: 在虚拟机参数位置加载 -Dspring.profiles.active=test
    //2、代码的方式激活某种环境；
    @Test
    public void testProfile() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //1、创建一个applicationContext
        //2、设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3、注册主配置类
        applicationContext.register(MainConfigOfProfile.class);
        //4、启动刷新容器
        applicationContext.refresh();

        Book bean = applicationContext.getBean(Book.class);
        System.out.println(bean);
        applicationContext.close();

        printBeansName(applicationContext);
    }

    private void printBeansName(ApplicationContext apc) {
        String[] beansName = apc.getBeanDefinitionNames();
        for (int i = 0, len = beansName.length ; i < len ; i ++) {
            System.out.println(beansName[i]);
        }
    }
}
