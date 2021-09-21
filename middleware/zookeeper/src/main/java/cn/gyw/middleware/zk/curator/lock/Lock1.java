package cn.gyw.middleware.zk.curator.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单个JVM 实现数据一致性
 */
public class Lock1 {

    static ReentrantLock reentrantLock = new ReentrantLock();
    static int count = 10;

    public static void generateNo() {
        try {
            reentrantLock.lock();
            count --;
            System.out.println(count);
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i =0 ;i < 10;i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    generateNo();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }, "t" + i).start();
        }

        countDownLatch.countDown();

        TimeUnit.SECONDS.sleep(3);
    }

}
