package cn.gyw.spring.quartz.trigger;

import cn.gyw.spring.quartz.QuartzContext;
import cn.gyw.spring.quartz.detail.JobInfo;
import cn.gyw.spring.quartz.job.SimpleJob;
import cn.gyw.spring.quartz.task.SimpleTask;
import cn.gyw.spring.quartz.detail.JobInfos;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by guanyw on 2018/8/3.
 */
@Component
@DependsOn(value = "jobInfoParser")
public class CronTriggerRunner {

	private static Logger logger = LoggerFactory.getLogger(CronTriggerRunner.class);

	@Autowired
	private JobInfos jobInfos;

	@PostConstruct
	public void handleTask() {
		try {
			for(JobInfo jobInfo : jobInfos.getJobInfoList()) {
				SimpleTask task = (SimpleTask) Class.forName(jobInfo.getClassName()).newInstance();
				task.setJobInfo(jobInfo);
				addTask(task);
			}
			startTask();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Quartz 定时任务调度
	 * @param task
	 */
	public void addTask(SimpleTask task) throws SchedulerException {
		logger.info("add task ,id:"+task.getJobInfo().getJobId()
				+ ",cron:"+task.getJobInfo().getCron());

		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
				.withIdentity(task.getJobInfo().getJobId(), "group-test")
				.build();
		jobDetail.getJobDataMap().put(QuartzContext.jobKey, task);
		CronTrigger cronTrigger;
		cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity(task.getJobInfo().getJobId(), "group-test")
				.withSchedule(CronScheduleBuilder.cronSchedule(task.getJobInfo().getCron()))
				.build();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.scheduleJob(jobDetail, cronTrigger);

		boolean immediate = task.getJobInfo().isImmediate();
		if (immediate) {
			logger.info("immediate run task :{}", jobDetail.getKey());
			scheduler.triggerJob(jobDetail.getKey());
		}

		Date nextDate = cronTrigger.getNextFireTime();
		Date nextDate1 = cronTrigger.getFireTimeAfter(nextDate);

		logger.info("task execute next time :{}", nextDate);
		logger.info("task execute next time :{}", nextDate1);
		logger.info("during is :{} s", (nextDate1.getTime() - nextDate.getTime())/1000);
	}

	public void startTask() throws SchedulerException {
		logger.info("all tasks start...");
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
	}
}
