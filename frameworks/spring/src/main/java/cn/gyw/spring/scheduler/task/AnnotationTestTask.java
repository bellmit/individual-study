package cn.gyw.spring.scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过注解的方式添加定时任务Task
 *
 * Created by guanyw on 2018/8/2.
 */
//@Component
public class AnnotationTestTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationTestTask.class);

	// 注解定义调度任务的方法，这个方法会在spring的ioc中被封装成一个Runnable对象，
	// 如果定义了cron表达式则会进入到CronTask的Runnable中，后面会对调度流程进一步分析
//	@Scheduled(cron = "${dev.scheduler.cron}")
	public void testOne() {
		LOGGER.info("======AnnotationTestTask is running");
	}
}
