package cn.gyw.corejava.concurrent.performance.deadlock;

import cn.gyw.corejava.concurrent.CustomExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 最简单的锁顺序死锁
 */
public class LeftRightDeadLock {

    private Object left = new Object();
    private Object right = new Object();

    public void leftRight() {
        // 获取left锁
        synchronized (left) {
            // do
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "get left locks");

            synchronized (right) {
                System.out.println(Thread.currentThread().getName() + "get right locks");
            }

        }
    }

    public void rightLeft() {
        // 获取left锁
        synchronized (right) {
            // do
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "get right locks");

            synchronized (left) {
                System.out.println(Thread.currentThread().getName() + "get left locks");
            }

        }
    }

    public static void main(String[] args) {
        ExecutorService exec = CustomExecutors.newTimingThreadPool();
        final LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                leftRightDeadLock.leftRight();
            }
        });

        exec.execute(new Runnable() {
            @Override
            public void run() {
                leftRightDeadLock.rightLeft();
            }
        });

        exec.shutdown();
        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
