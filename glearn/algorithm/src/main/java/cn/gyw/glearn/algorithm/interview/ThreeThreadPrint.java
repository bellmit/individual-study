package cn.gyw.glearn.algorithm.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 三个线程轮流打印 1~100
 * 
 * 条件：
 * 必须轮流，t1-1,t2-2,t3-3
 */
public class ThreeThreadPrint {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		PrintTask task = new PrintTask(latch);
		Thread t1 = new Thread(task, "t1");
		Thread t2 = new Thread(task, "t2");
		Thread t3 = new Thread(task, "t3");
		
		t1.start();
		t2.start();
		t3.start();
		
		latch.countDown();
	}
	
	private static class PrintTask implements Runnable {

		private int incr = 1;
		
		private volatile int t = 0;
		
		CountDownLatch latch;
		
		private Map<String, Thread> waiters = new HashMap<>();
		
		PrintTask(CountDownLatch latch) {
			this.latch = latch;
		}
		
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while(incr <=100) {
				if (curThreadName().equals(Thread.currentThread().getName())) {
					System.out.println(Thread.currentThread().getName() + " >> " + (incr ++));
					t = t % 3 + 1;
					LockSupport.unpark(waiters.get(curThreadName()));
				} else {
					waiters.put(Thread.currentThread().getName(), Thread.currentThread());
					LockSupport.park();
				}
			}
			
			waiters.values().forEach(t -> {
				t.interrupt();
			});
		}
		
		private String curThreadName() {
			if (t == 0) {
				return "t1";
			}
			if (t == 1) {
				return "t2";
			}
			if (t == 2) {
				return "t3";
			}
			return "t1";
		}
	}
	
	private static class PrintTask1 implements Runnable {

		private int incr = 1;

		@Override
		public void run() {
			
		}
	}
}
