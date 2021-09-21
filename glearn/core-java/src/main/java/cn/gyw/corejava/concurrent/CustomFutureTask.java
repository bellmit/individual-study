package cn.gyw.corejava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

/**
 * 手写FutureTask
 */
public class CustomFutureTask<T> implements RunnableFuture<T> {
	
	private Callable<T> callable;
	
	private T result;
	
	private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<Thread>();
	
	private volatile String state = "NEW";
	
	public CustomFutureTask(Callable<T> callable) {
		this.callable = callable;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		if ("END".equals(state)) {
			return result;
		}
		while (!"END".equals(state)) {
			waiters.add(Thread.currentThread());
			LockSupport.park();
		}
		return result;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public void run() {
		try {
			result = callable.call();
			state = "END";
			Thread waiter = null;
			while (!waiters.isEmpty()) {
				waiter = waiters.poll();
				LockSupport.unpark(waiter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
