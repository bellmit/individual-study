package cn.gyw.corejava.concurrent.locks;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子包 测试
 *
 * Created by guanyw on 2018/7/12.
 */
public class AtomicDemo implements Runnable {

	private volatile int icounter = 0;

	// 原子int 操作
	private int counter = 0;
    protected AtomicInteger atomicInteger = new AtomicInteger(0);

    // 原子int 数组，元素可以原子性更新
    protected AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(4);

    // 对指定类的指定的volatile int字段进行原子更新
    protected AtomicIntegerFieldUpdater atomicIntegerFieldUpdater =
			AtomicIntegerFieldUpdater.newUpdater(AtomicDemo.class, "icounter");

	private CountDownLatch countDownLatch;

	public AtomicDemo(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
//		nonAtomicInt();
//		atomicInt();
		atomicUpdater();
		countDownLatch.countDown();
	}

	/*
	非原子操作
	 */
	private void nonAtomicInt() {
		printWithThread(counter);
		for (int i = 0 ; i < 1000 ; i++) {
			counter++;
		}
		printWithThread(counter);
	}

	/*
	原子操作
	 */
	private void atomicInt() {
		printWithThread(atomicInteger.get());
		for (int i = 0 ; i < 1000 ; i++) {
			atomicInteger.incrementAndGet();
		}
		printWithThread(atomicInteger.get());
	}

	public void atomicUpdater() {
		for (int i = 0 ; i < 1000 ; i++) {
			atomicIntegerFieldUpdater.addAndGet(this, (++icounter));
		}
	}

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(1000);
		AtomicDemo runnable = new AtomicDemo(countDownLatch);
		/*
		ExecutorService executorService = CustomThreadPoolFactory.newCustomThreadPool();
		for (int i = 0 ; i < 2 ; i ++) {
			executorService.execute(runnable);
		}
		executorService.shutdown();
		*/
		for (int i = 0 ; i < 1000 ; i ++) {
			new Thread(runnable).start();
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("---->" + runnable.icounter);
	}

	public void printWithThread(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append(Thread.currentThread().getName())
                .append(":")
                .append(obj);
        System.out.println(builder.toString());
    }

}
