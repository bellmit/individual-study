package cn.gyw.spring.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by guanyw on 2018/8/3.
 */
@Component
public class QuartzContext {

	private final static Logger logger = LoggerFactory.getLogger(QuartzContext.class);

	public static String timeFormat = "yyyy-MM-dd hh24:mi:ss";
    public static String jobKey = "myJob";
    public static String defaultClass = "com.eastcom.linksight.data.sender.task.DataTask";
    public static String localPath;
	public static String collectHistoryRecordFile;

	public String getLocalPath() {
		return localPath;
	}

	public static void setLocalPath(String localPath) {
		QuartzContext.localPath = localPath;
	}

	public String getCollectHistoryRecordFile() {
		return collectHistoryRecordFile;
	}

	public static void setCollectHistoryRecordFile(String collectHistoryRecordFile) {
		QuartzContext.collectHistoryRecordFile = collectHistoryRecordFile;
	}
}
