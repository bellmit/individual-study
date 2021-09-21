package cn.gyw.corejava.concurrent.aqs;

import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义的二元闭锁
 *
 * 闭锁是关的，任何调用线程都会获取失败，阻塞
 * 闭锁是开的，
 */
@ThreadSafe
public class OneShotLatch {

    private Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }

    // 多个线程可以同时获取
    private class Sync extends AbstractQueuedSynchronizer {

        Sync() {
            setState(1);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            // 如果闭锁是开的，获取成功，否则失败
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            // 打开闭锁
            setState(1);
            // 其他线程可以获取该闭锁
            return true;
        }
    }

}
