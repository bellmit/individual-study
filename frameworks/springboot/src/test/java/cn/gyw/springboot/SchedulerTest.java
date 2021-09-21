package cn.gyw.springboot;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.gyw.springboot.scheduler.SchedulerConfig;

/**
 * 定时任务测试
 */
public class SchedulerTest {

	private ApplicationContext applicationContext;
	
	@Before
	public void before() {
		this.applicationContext = new AnnotationConfigApplicationContext(SchedulerConfig.class);
	}
	
	@Test
	public void test() {
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void after() {
		
	}
	
}
