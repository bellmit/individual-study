package cn.gyw.corejava.concurrent;

import java.util.concurrent.*;

/**
 * AQS 组件
 */
public class AQSTest {

    public void countDownLatch() {
        final int threadCount = 200;

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch latch = new CountDownLatch(200);

        for (int i = 0 ; i < threadCount ; i ++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println(">>" + threadNum);
                } catch (Exception e) {
                    // error
                } finally {
                    // 如果不论如何都执行，建议在finally 中使用
                    latch.countDown();
                }
            });
        }

        try {
            // latch.await();
            latch.await(10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish ...");
        exec.shutdown();
    }

    /**
     * 许可量
     */
    public void semaphore() {
        final int threadCount = 200;

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0 ; i < threadCount ; i ++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    semaphore.acquire(); // 获取许可
                    // semaphore.tryAcquire(); // 尝试获取许可
                    // semaphore.tryAcquire(1, TimeUnit.SECONDS); // 尝试1秒等待
                    Thread.sleep(100);
                    System.out.println(">>" + threadNum);
                    semaphore.release(); // 释放许可
                } catch (Exception e) {
                    // error
                }
            });
        }

        System.out.println("finish ...");
        exec.shutdown();
    }

    public void cyclicBarrier() {

        // 循环等待
        final CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("callback ...");
        });

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0 ; i < 10 ; i ++) {
            final int threadNum = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            exec.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(threadNum + "is ready");
                    barrier.await();
                    // barrier.await(2000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                } /*catch (TimeoutException e) {
                    e.printStackTrace();
                }*/
                System.out.println(threadNum + " continue");
            });
        }

        exec.shutdown();
    }

    public static void main(String[] args) {
        AQSTest aqsTest = new AQSTest();
        // aqsTest.countDownLatch();
        aqsTest.cyclicBarrier();
    }
}
