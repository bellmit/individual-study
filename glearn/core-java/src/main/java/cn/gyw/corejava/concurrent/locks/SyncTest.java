package cn.gyw.corejava.concurrent.locks;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by guanyw on 2018/7/12.
 */
public class SyncTest extends AbstractLockTemplate {

	public SyncTest(String id, int round, int threadNum, CyclicBarrier barrier) {
		super(id, round, threadNum, barrier);
	}

	@Override
	synchronized void sumValue() {
		super.conutValue += preInit[index++%round];
	}

	@Override
	synchronized long getValue() {
		return super.conutValue;
	}
}
