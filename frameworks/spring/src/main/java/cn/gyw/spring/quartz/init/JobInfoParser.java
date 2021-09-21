package cn.gyw.spring.quartz.init;

import cn.gyw.spring.quartz.detail.JobInfo;
import cn.gyw.spring.quartz.detail.JobInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by guanyw on 2018/8/3.
 */
@Component(value = "jobInfoParser")
@DependsOn(value = {"quartzContext"})
public class JobInfoParser {

	@Autowired
	private JobInfos jobInfos;

	@PostConstruct
	public void init() {
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobId("sayHelloTask");
		jobInfo.setCron("0/1 * * * * ?");
		jobInfo.setClassName("SayHelloTask");

		jobInfos.getJobInfoList().add(jobInfo);
	}
}
