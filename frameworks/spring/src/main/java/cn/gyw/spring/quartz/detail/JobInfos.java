package cn.gyw.spring.quartz.detail;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by guanyw on 2018/8/3.
 */
@Component
public class JobInfos {

	private List<JobInfo> jobInfoList = new LinkedList<>();

	public List<JobInfo> getJobInfoList() {
		return jobInfoList;
	}

	public void setJobInfoList(List<JobInfo> jobInfoList) {
		this.jobInfoList = jobInfoList;
	}
}
