package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发环境
 */
public abstract class BaseCondition {

    // 请求总数
    protected static int clientTotal = 5000;

    // 同时并发线程数
    protected static int threadTotal = 200;

    /**
     * 并发执行任务
     *
     * @throws InterruptedException
     */
    public void work() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            final Semaphore semaphore = new Semaphore(threadTotal);
            final CountDownLatch latch = new CountDownLatch(clientTotal);

            for (int i = 0; i < clientTotal; i++) {
                final Integer index = Integer.valueOf(i);
                executorService.execute(() -> {
                    try {
                        semaphore.acquire();
                        buildTask(index);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                });
            }

            latch.await();
            executorService.shutdown();
            printInfo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定义并发任务
     */
    protected abstract void buildTask(Integer index);

    /**
     * 结果打印
     */
    protected abstract void printInfo();
}
