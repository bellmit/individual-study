package cn.gyw.corejava.concurrent.demo;

import cn.gyw.corejava.exceptions.ExUtil;

import java.util.concurrent.*;

/**
 * 计时运行
 */
public class TimedRun {

    // 周期调度线程池
    private static final ScheduledExecutorService cancelExec = Executors.newSingleThreadScheduledExecutor();

    // 任务执行框架
    private static final ExecutorService taskExec = Executors.newFixedThreadPool(4);

    /**
     * 通过现有类库Future 来取消任务
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timeRun3(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // 接下来任务将被取消
        } catch (ExecutionException e) {
            // 如果在任务中抛出异常，那么重新抛出该异常
            throw ExUtil.launderThrowable(e.getCause());
        } finally {
            // 如果任务已经结束，那么执行取消任务不会带来影响
            task.cancel(true); // 如果任务正在执行，那么将被中断
        }
    }

    /**
     * 在专门的线程中断任务
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timeRun2(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {

        class RethrowableTask implements Runnable {

            // 线程间共享
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable e) {
                    this.t = e.getCause();
                }
            }

            void rethrow() {
                if (t != null) {
                    throw ExUtil.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);

        // 问题：无法知道是线程执行完成退出还是join 超时退出
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    /**
     * 在外部调用线程中安排中断（不要这么做）
     *
     * 破坏了规则：在线程中断前，了解它的中断策略
     *
     * 任何线程都可能调用timeRun 方法，不能了解其中断策略
     * 如果任务在超时之前执行完，此时调用了中断，可能会影响现有线程
     *
     * @param r
     * @param timeout
     * @param unit
     */
    public static void timeRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();

        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);

        r.run();
    }
}
