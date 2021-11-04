package cn.gyw.spring.scheduler;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SchedulerMain {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-scheduler.xml");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
