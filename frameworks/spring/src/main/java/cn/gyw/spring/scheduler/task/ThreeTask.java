package cn.gyw.spring.scheduler.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanyw on 2018/8/3.
 */
//@Component
public class ThreeTask {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void execute() {
		System.out.println("3当前Task-Three-开始时间：" + sdf.format(new Date()));
		try {
//			System.out.println("3当前Task-Three 休眠：" + 8 + "秒" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("3当前Task-Three-结束时间：" + sdf.format(new Date()));
	}

}
