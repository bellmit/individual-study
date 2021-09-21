package cn.gyw.corejava.concurrent.performance;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可定时的锁
 */
public class TryLockAvoidDeadLock {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    lock.tryLock(1, TimeUnit.SECONDS);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                while (true) {
                }
            }
        });

        t1.start();
        t2.start();

    }
}
