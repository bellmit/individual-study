package cn.gyw.corejava.concurrent.threadpool;

import cn.gyw.corejava.concurrent.CustomExecutors;
import cn.gyw.corejava.util.SystemUtil;
import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * 可阻塞线程池
 *
 * 具有饱和策略：队列满则execute 阻塞
 */
@ThreadSafe
public class BoundedExecutor {

    private final Executor exec;
    private Semaphore semaphore;

    public BoundedExecutor(int maxTask) {
        this.exec = CustomExecutors.newFixedPoolByRunnerCallers(SystemUtil.getCpuCnt() + 1);
        this.semaphore = new Semaphore(maxTask);
    }

    // 任务提交会阻塞
    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        // 任务执行完成，释放许可
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) { // 任务拒绝
            // 释放许可
            semaphore.release();
        }
    }
}
