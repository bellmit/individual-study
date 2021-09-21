package cn.gyw.corejava.concurrent;

import cn.gyw.corejava.concurrent.task.CounterTask;
import cn.gyw.corejava.concurrent.task.Task;
import cn.gyw.corejava.concurrent.task.processor.FutureTaskProcess;
import cn.gyw.corejava.concurrent.thread.pool.CustomThreadPoolFactory;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable 接口的测试
 */
public class CallableTest {

    /**
     * 通过FutureTask包装器包装Future
     */
    @Test
    public void shouldAsyncGetResultWithFutureTask() {
        // 计数task
        Task task = new CounterTask();
        FutureTaskProcess taskProcess = new FutureTaskProcess(task);

        FutureTask<Task> futureTask = new FutureTask<>(taskProcess);

        Thread thread = new Thread(futureTask, "futureTask 线程");
        thread.start();

        try {
            Task task1 = futureTask.get();
            System.out.println(task1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用线程池
     */
    @Test
    public void shouldAsyncGetResult() {
        ExecutorService executorService = CustomThreadPoolFactory.newCustomThreadPool();
        // 计数task
        Task task = new CounterTask();
        FutureTaskProcess taskProcess = new FutureTaskProcess(task);
        Future<Task> future = executorService.submit(taskProcess);
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            // 中断异常，任务可中断
            e.printStackTrace();
        } catch (ExecutionException e) {
            // 执行异常，捕获执行异常
            e.printStackTrace();
        }
    }
}
