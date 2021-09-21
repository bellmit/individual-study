package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Executor 执行框架测试
 */
public class ExecutorTest {

    /**
     * 时间线程池
     */
    @Test
    public void timingThreadPool() {
        ExecutorService exec = CustomExecutors.newTimingThreadPool();

        for (int i = 0 ; i < 10 ; i ++) {
            exec.submit(new TempTask(i));
        }

        exec.shutdown();

        try {
            exec.awaitTermination(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 线程池
     */
    @Test
    public void maxPoolSize() {
        int corePoolSize = 3;
        int maximumPoolSize = 5;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = null;
        // 静默丢弃任务
        handler = new ThreadPoolExecutor.DiscardPolicy();
        // 丢弃最老的
        // handler = new ThreadPoolExecutor.DiscardOldestPolicy();

        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);

        ExecutorServiceInfoService infoService = new ExecutorServiceInfoService(executorService);
        infoService.start();

        // 完成服务，获取所有完成的任务
        ExecutorCompletionService<Integer> ecs = new ExecutorCompletionService<>(executorService);
        doWork(ecs);

        try {
            ecs.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWork(final ExecutorCompletionService<Integer> ecs) {
        CompletionService<Integer> ex = ecs;

        List<Future<Integer>> results = new ArrayList<>();
        ExecutorService fixedPool = Executors.newFixedThreadPool(1);
        fixedPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " start task");
            for (int i = 0; i < 100; i++) {
                final int taskId = i;
                ex.submit(new TempTask(taskId));
            }
        });
    }

    // 带id 的任务
    private class TempTask implements Callable<Integer> {

        private int id;

        TempTask(int id) {
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over task id: " + this.id);
            return this.id;
        }
    }
}
