package cn.gyw.spring.quartz.job;

import cn.gyw.spring.quartz.QuartzContext;
import cn.gyw.spring.quartz.task.SimpleTask;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanyw on 2018/8/3.
 */
@DisallowConcurrentExecution    //阻止并发执行(上一个任务执行完，才会执行相同的下一个任务)
@PersistJobDataAfterExecution
public class SimpleJob implements Job {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobKey jobKey = context.getJobDetail().getKey();
		logger.info("Simple Job says :{}, executing at ：{}.", jobKey, longSdf.format(new Date()));
		SimpleTask task = (SimpleTask) context.getMergedJobDataMap().get(QuartzContext.jobKey);
		task.execute();
	}
}
