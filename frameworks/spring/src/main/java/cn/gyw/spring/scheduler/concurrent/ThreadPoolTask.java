package cn.gyw.spring.scheduler.concurrent;

import cn.gyw.spring.scheduler.task.ThreadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 多任务并行调度
 * Created by guanyw on 2018/8/3.
 */
//@Component(value = "threedPoolTask")
public class ThreadPoolTask implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(ThreadPoolTask.class);

	private ApplicationContext applicationContext;

//	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	final CountDownLatch countDownLatch = new CountDownLatch(3);

	public void testjobThread() {
		try {
			CountDownLatch latch = new CountDownLatch(3);  //java工具类，类似与计数器，主要实现子线程未结束钱，主线程一直等待
			ThreadTask test1 = (ThreadTask) applicationContext.getBean("threadTask", "test1", latch);
			ThreadTask test2 = (ThreadTask) applicationContext.getBean("threadTask", "test2", latch);
			ThreadTask test3 = (ThreadTask) applicationContext.getBean("threadTask", "test3", latch);
			taskExecutor.execute(test1);
			taskExecutor.execute(test2);
			taskExecutor.execute(test3);
			latch.await(); //子线程未结束前，一直等待
			//test1.run();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
