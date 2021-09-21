package cn.gyw.spring.scheduler.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanyw on 2018/7/27.
 */
//@Component
public class TwoTask {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private int count = 0;

	public void execute() {
		System.out.println("2当前Task-Two-当前时间：" + sdf.format(new Date()));
		try {
			System.out.println("2当前Task-Two 休眠：" + 8 + "秒" );
			Thread.sleep(8 * 1000);
		} catch (InterruptedException e) {
		}
		System.out.println("2当前Task-Two-结束时间：" + sdf.format(new Date()));
	}

}
