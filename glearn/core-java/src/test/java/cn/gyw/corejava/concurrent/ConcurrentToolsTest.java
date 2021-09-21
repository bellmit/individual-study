package cn.gyw.corejava.concurrent;

import cn.gyw.corejava.concurrent.task.processor.CyclicBarrierProcess;
import cn.gyw.corejava.concurrent.task.processor.GetSingletonProcess;
import cn.gyw.corejava.concurrent.thread.SimpleWorker;
import cn.gyw.corejava.model.SingletonObject;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * java util concurrent 包的Tools 工具测试
 *
 * 1）CountdownLatch
 * 2）CyclicBarrier
 * 3）Semaphore
 * 4）Exchanger
 */
public class ConcurrentToolsTest {


    @Test
    public void executorPool() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
//        executor.


    }

    /**
     * CountdownLatch的简单实现
     * @throws InterruptedException
     */
    @Test
    public void countDownLatch() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        System.out.println("主线程开始执行...");
        new SimpleWorker(downLatch).start();
        System.out.println("主线程等待...");
        System.out.println(downLatch.toString());
        downLatch.await();
        System.out.println(downLatch.toString());
        System.out.println("主线程继续...");
    }

    /**
     * 测试：多个线程并发获取单例
     */
    @Test
    public void countdownLatchUsage() throws InterruptedException, ExecutionException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        GetSingletonProcess process = new GetSingletonProcess(countDownLatch);
        FutureTask<SingletonObject> worker1 = new FutureTask<>(process);
        new Thread(worker1).start();
        countDownLatch.countDown();
        FutureTask<SingletonObject> worker2 = new FutureTask<>(process);
        new Thread(worker2).start();
        countDownLatch.countDown();
        FutureTask<SingletonObject> worker3 = new FutureTask<>(process);
        new Thread(worker3).start();
        countDownLatch.countDown();

        System.out.println(worker1.get());
        System.out.println(worker2.get());
        System.out.println(worker3.get());

    }

    /**
     * 回环栅栏测试
     * 写线程所有都成功后，才能进行下一步操作
     */
    @Test
    public void cyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        CyclicBarrierProcess process = new CyclicBarrierProcess(cyclicBarrier);

        new Thread(process).start();
        new Thread(process).start();
        new Thread(process).start();

        Thread.sleep(25000);
        System.out.println("开始读取数据");

    }

    /**
     * 信号量的使用
     */
    @Test
    public void semaphoreUsage() throws InterruptedException {
        Semaphore semaphore = new Semaphore(5, false);
        for (int i = 0 ; i < 10 ; i ++) {
            new Thread(() ->{
                try {
                     // 获取许可
                    semaphore.acquire();
                    Thread.sleep(500);
                    System.out.println(LocalDateTime.now() +Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放许可
                    semaphore.release();
                }
            }).start();

        }
        // 等待10s
        Thread.sleep(10000);
    }

    /**
     * ExcnagerTest
     */
    @Test
    public void exchangerTest() throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();
        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);
        new Thread(producer).start();
        new Thread(consumer).start();

        TimeUnit.SECONDS.sleep(8);
    }

    private static volatile boolean isDone = false;

    static class ExchangerProducer implements Runnable {

        private Exchanger<Integer> exchanger;
        private static int data = 1;

        ExchangerProducer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone) {
                for (int i =1;i<4;i++) {
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        data = i;
                        System.out.println("producer before :" + data);
                        data = exchanger.exchange(data);
                        System.out.println("producer after exchange :" + data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDone = true;
            }
        }
    }

    static class ExchangerConsumer implements Runnable {

        private Exchanger<Integer> exchanger;
        private static int data = 0;

        ExchangerConsumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone) {
                data = 0;
                try{
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("consumer before exchange:" + data);
                    data = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("consumer after exchange :" + data);
            }
        }
    }

}
