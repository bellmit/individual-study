package cn.gyw.spring.scheduler;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guanyw on 2018/7/27.
 */
public class SchedulerMain {

	public static void main(String[] args) {

		new ClassPathXmlApplicationContext("spring/scheduler/applicationContext.xml");
	}
}
