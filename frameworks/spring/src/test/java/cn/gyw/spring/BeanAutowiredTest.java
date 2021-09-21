package cn.gyw.spring;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.gyw.spring.config.MainConfigOfAutowired;
import cn.gyw.spring.model.BookStore;
import cn.gyw.spring.web.controller.HelloController;

/**
 * Bean 自动装配
 */
public class BeanAutowiredTest extends AbstractTest {

    public BeanAutowiredTest() {
        super(MainConfigOfAutowired.class);
    }

    @Test
    public void testDI() {
        HelloController helloController = apc.getBean(HelloController.class);
        System.out.println(helloController);

        BookStore bookStore = apc.getBean(BookStore.class);
        System.out.println(bookStore);


    }

    @After
    public void after() {
        System.out.println("---------------------------------");
        printBeansName(apc);
    }

    private void printBeansName(ApplicationContext apc) {
        String[] beansName = apc.getBeanDefinitionNames();
        for (int i = 0, len = beansName.length ; i < len ; i ++) {
            System.out.println(beansName[i]);
        }
    }
}
