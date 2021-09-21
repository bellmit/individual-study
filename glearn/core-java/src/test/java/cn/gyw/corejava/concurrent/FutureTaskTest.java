package cn.gyw.corejava.concurrent;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * FutureTask 学习
 */
public class FutureTaskTest {

	/**
	 * FutureTask 运行原理
	 */
	@Test
	public void futureTask() throws ExecutionException, InterruptedException {
		FutureTask<String> futureTask = new FutureTask<>(()-> "123");
		new Thread(futureTask).start();
		String ret = futureTask.get();
		System.out.println("ret >>" + ret);
	}

	@Test
	public void shouldRunWithCustomTask() throws Exception {
		Callable<Object> callable = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				return "this is callable";
			}
		};
		
		CustomFutureTask<Object> task = new CustomFutureTask<Object>(callable);
		
		for (int i = 0 ; i < 1000000 ; i ++) {
			new Thread(task).start();			
			Object result = task.get();
			if (Objects.isNull(result)) {
				System.out.println("error concurrent");				
			}
		}
	}
}
