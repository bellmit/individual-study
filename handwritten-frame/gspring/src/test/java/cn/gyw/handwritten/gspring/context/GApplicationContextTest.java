package cn.gyw.handwritten.gspring.context;

import org.junit.jupiter.api.Test;

import cn.gyw.handwritten.gspring.demo.HelloController;

class GApplicationContextTest {

    @Test
    public void run() {
        GApplicationContext applicationContext =
                new GApplicationContext("application.properties");

        try {
            Object object = applicationContext.getBean("helloService");
            System.out.println("1>> " + object);
            object = applicationContext.getBean(HelloController.class);
            System.out.println("2>> " + object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}