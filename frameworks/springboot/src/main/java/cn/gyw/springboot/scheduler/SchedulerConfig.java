package cn.gyw.springboot.scheduler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务
 */
@EnableScheduling
@Configuration
@ComponentScan(basePackages = { "cn.gyw.springboot.scheduler" })
public class SchedulerConfig {

	private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);
	
	/**
	 * 线程池
	 * @return
	 */
	@Bean
    public TaskScheduler taskScheduler() {
        int poolSize = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        scheduler.setBeanName("task-scheduler");
        log.info("ThreadPoolTaskScheduler poolSize:{}, availableProcessors:{}", scheduler.getPoolSize(), poolSize);
        scheduler.initialize();
        return scheduler;
    }
	
	// @Scheduled(fixedRate = 1000)
	public void task1() {
		log.info("Task 1 run start");
		try {
			TimeUnit.SECONDS.sleep(3L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Task 1 run end");
	}
	
	
	// @Scheduled(fixedRate = 1000)
	public void task2() {
		log.info("Task 2 run...");
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// @Scheduled(fixedRate = 1000)
	public void task3() {
		log.info("Task 3 run...");
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
