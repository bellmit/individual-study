package cn.gyw.corejava.concurrent.demo;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 在ExecutorService 中跟踪在关闭后被取消的任务
 *
 * 不可避免的竞态条件，从而产生“误报”问题：
 * 一些认为已取消的任务实际上已经执行完成。
 *
 * 原因：
 * 任务执行的最后一条指令以及线程池将任务记录为“结束”的两个时刻之间，线程池可能关闭。
 * 任务是幂等的，不会存在问题，网络爬虫就是这样。
 * 否则，必须要考虑这种风险，处理误报的任务。
 *
 * @see WebCrawler
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;

    private final Set<Runnable> taskCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService executorService) {
        this.exec = executorService;
    }
    @Override
    public void execute(Runnable command) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    if (isShutdown() && Thread.currentThread().isInterrupted()) {
                        taskCancelledAtShutdown.add(command);
                    }
                }
            }
        });
    }

    // 获取正在运行被取消的任务
    public List<Runnable> getCancelledTasks() {
        if (!exec.isShutdown()) {
            throw new IllegalStateException("task is running ...");
        }
        return new ArrayList<>(taskCancelledAtShutdown);
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

}
