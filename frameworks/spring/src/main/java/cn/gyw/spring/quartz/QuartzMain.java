package cn.gyw.spring.quartz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guanyw on 2018/8/3.
 */
public class QuartzMain {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring/quartz/applicationContext.xml");
	}
}
