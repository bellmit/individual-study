package cn.gyw.corejava.concurrent.locks;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by guanyw on 2018/7/12.
 */
public class ReenLockDemo extends AbstractLockTemplate {

	public ReenLockDemo(String id, int round, int threadNum, CyclicBarrier barrier) {
		super(id, round, threadNum, barrier);
	}

	ReentrantLock lock = new ReentrantLock();

	@Override
	void sumValue() {
		try {
			lock.lock();
			super.conutValue += preInit[index++%round];
		} finally {
			lock.unlock();
		}
	}

	@Override
	long getValue() {
		try {
			lock.lock();
			return super.conutValue;
		} finally {
			lock.unlock();
		}
	}
}
