package cn.gyw.spring.scheduler.task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by guanyw on 2018/8/3.
 */
//@Component("threadTask")
//@Scope("prototype")
public class ThreadTask extends Thread {

	/**
	 * spring tash默认是单线程 串行执行，即一个方法执行完成前，后面的job不会执行的
	 * 但是如果主方法里面产生了thread线程， 主线程如果不等子线程结束后 就结束的话， task任务会产生多次调度
	 */

	private String Treadname;
	private CountDownLatch latch;

	public ThreadTask(String Treadname, CountDownLatch latch) {
		this.Treadname = Treadname;
		this.latch = latch;
	}

	@Override
	public void run() {

		try {
			//主业务方法
			for (int i = 0; i < 10; i++) {
				Thread current = Thread.currentThread();
				System.out.println("线程号：" + current.getId() + "--" + current.getName() + " --" + Treadname
						+ ":---runing--- " + i + "--" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				Thread.sleep(20000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//设置实例 执行完毕
			latch.countDown();
		}

	}

	public void setTreadname(String treadname) {
		Treadname = treadname;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
}
