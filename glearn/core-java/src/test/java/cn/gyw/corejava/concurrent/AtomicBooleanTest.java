package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发线程数
    public static int threadTotal = 200;

    public static AtomicBoolean isHappend = new AtomicBoolean(false);

    @Test
    public void shouldRunSafe() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch latch = new CountDownLatch(clientTotal);

        for (int i = 0 ; i < clientTotal ; i ++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();
    }

    private void test() {
        AtomicBoolean atomicBoolean = isHappend;
        if (atomicBoolean.compareAndSet(false, true)) {
            // 控制该逻辑仅被执行一次
            System.out.println("execute...>" + isHappend);
        }
    }

}
