package cn.gyw.corejava.concurrent;

import cn.gyw.platform.annotations.ThreadSafe;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@ThreadSafe
public class LongAdderTest {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发线程数
    public static int threadTotal = 200;

    /**
     * 优点：在高并发的情况下，atomic 因为do-while循环的问题，失败概率很大，竞争激烈；
     * longAdder 使用分成多段，提高并发度
     * 缺点：并发高的情况下，可能失精度
     */
    public static LongAdder count = new LongAdder();

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
        LongAdder counter = count;
        counter.increment();
    }

}
