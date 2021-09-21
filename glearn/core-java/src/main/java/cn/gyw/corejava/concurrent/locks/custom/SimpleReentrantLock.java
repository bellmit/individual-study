package cn.gyw.corejava.concurrent.locks.custom;

/**
 * 自定义的可重入锁
 *
 * 非公平锁，synchronized 实现
 * Created by guanyw on 2019/1/24.
 */
public class SimpleReentrantLock {

    /**
     * 锁标志
     */
    private volatile boolean lockMonitor = false;

    /**
     * 当前获取锁的线程
     */
    private Thread lockedBy = null;

    /**
     * 锁次数
     */
    private int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ", 请求锁");
        Thread callingThread = Thread.currentThread();
        while (testAndSet() && callingThread != lockedBy) {
            wait();
        }
        lockedCount++;
        lockedBy = callingThread;
    }

    public synchronized void unlock() {
        System.out.println(Thread.currentThread().getName() + ", 释放锁");
        if (Thread.currentThread() == lockedBy) {
            // 如果锁是当前线程锁
            lockedCount--;

            if (lockedCount == 0) {
                releaseLock();
                notify();
            }
        }
    }

    /**
     * 操作原子执行
     * 如果lock 初始值为false，则被设置为true，函数返回false，意味着抢到锁了
     * 如果lock 初始值为true，则仍然设置为true，函数返回true，意味着锁占用
     * @return
     */
    private boolean testAndSet() {
        boolean rv = lockMonitor;
        lockMonitor = true;
        return rv;
    }

    private void releaseLock() {
        lockMonitor = false;
    }
}
