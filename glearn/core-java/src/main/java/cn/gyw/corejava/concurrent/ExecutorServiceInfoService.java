package cn.gyw.corejava.concurrent;

import java.util.concurrent.*;

/**
 * 自定义监控服务
 *
 * 启动线程打印目标线程池的运行情况
 */
public class ExecutorServiceInfoService {

    private final ExecutorService taskExec;

    public ExecutorServiceInfoService(ExecutorService exec) {
        this.taskExec = exec;
    }

    public void start() {
        // 启动监控
        runPrintThread(taskExec);
    }

    private void runPrintThread(final ExecutorService executorService) {
        Runnable task = null;
        if (executorService instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor exec = (ThreadPoolExecutor) executorService;
            task = new ThreadPoolExecutorInfo(exec);
        } else {
            task = new ExecutorServiceInfo(executorService);
        }
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        // 每1s 打印线程池情况
        es.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 线程池 运行信息任务
     */
    private class ThreadPoolExecutorInfo implements Runnable {

        private final ThreadPoolExecutor tpe;

        ThreadPoolExecutorInfo(ThreadPoolExecutor exec) {
            this.tpe = exec;
        }

        @Override
        public void run() {
            System.out.println("********************************");
            System.out.println(">> core pool size :" + tpe.getCorePoolSize());
            System.out.println(">> task queue size:" + tpe.getQueue().size());
            System.out.println(">> current pool size:" + tpe.getPoolSize());
            System.out.println(">> active count:" + tpe.getActiveCount());
            System.out.println(">> task count:" + tpe.getTaskCount());
            System.out.println(">> completed task count:" + tpe.getCompletedTaskCount());
            System.out.println("*********************************");
        }
    }

    // 执行服务 运行信息任务
    private class ExecutorServiceInfo implements Runnable {

        private final ExecutorService executorService;

        ExecutorServiceInfo(ExecutorService exec) {
            this.executorService = exec;
        }

        @Override
        public void run() {
            System.out.println("********************************");
            System.out.println(">> executor is shutdown :" + executorService.isShutdown());
            System.out.println("*********************************");
        }
    }
}
