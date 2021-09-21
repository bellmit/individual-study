package cn.gyw.spring.quartz.detail;

/**
 * Created by guanyw on 2018/8/3.
 */
public class JobInfo {

	private String jobId;

	private String className;

	private String cron;

	private boolean immediate = false;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}
}
