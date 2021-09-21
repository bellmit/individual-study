package cn.gyw.corejava.concurrent.locks.custom;

/**
 * 简单实现的自旋锁
 *
 * 非公平锁，synchronized 实现
 * 测试类：
 * SimpleCustomLockTest
 */
public class SimpleSpinLock {

    private volatile boolean lock = false;

    public synchronized void lock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ", 请求锁");
        // while 忙等待，防止假唤醒
        while (testAndSet()) {
            wait();
        }
        System.out.println(Thread.currentThread().getName() + ", 获取锁");
    }

    public synchronized void unlock() {
        releaseLock();
        notify();
    }

    /**
     * 操作原子执行
     * 如果lock 初始值为false，则被设置为true，函数返回false，意味着抢到锁了
     * 如果lock 初始值为true，则仍然设置为true，函数返回true，意味着锁占用
     * @return
     */
    private boolean testAndSet() {
        boolean rv = lock;
        lock = true;
        return rv;
    }

    private void releaseLock() {
        lock = false;
    }

}
