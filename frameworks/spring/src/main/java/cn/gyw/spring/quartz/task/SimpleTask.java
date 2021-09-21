package cn.gyw.spring.quartz.task;

import cn.gyw.spring.quartz.detail.JobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guanyw on 2018/8/3.
 */
public class SimpleTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private JobInfo jobInfo;

	/**
	 * Task 主要执行方法
	 */
	public void execute() {
		logger.info("{} 开始执行了.执行cron为：{}", jobInfo.getJobId(), jobInfo.getCron());
		try {
			Thread.sleep(3000);
			logger.info("休眠了3秒...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("{} 结束执行！", jobInfo.getJobId());
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
}
