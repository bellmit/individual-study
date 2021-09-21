package cn.gyw.corejava.concurrent;

import cn.gyw.platform.annotations.ThreadSafe;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class AtomicLongTest {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发线程数
    public static int threadTotal = 200;

    public static AtomicLong count = new AtomicLong(0L);

    @Test
    public void shouldRunSafe() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch latch = new CountDownLatch(clientTotal);

        for (int i = 0 ; i < clientTotal ; i ++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();
        System.out.println("count:" + count);
    }

    private void add() {
        AtomicLong counter = count;
        counter.incrementAndGet();
    }

}
