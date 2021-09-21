package cn.gyw.corejava.concurrent.demo;

import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * 日志服务
 *
 * 可靠的服务取消
 */
public class LogServiceByExecutorService {

    // 任务执行服务
    private final ExecutorService taskExec;

    private final BlockingQueue<String> queue;

    private PrintWriter writer;

    public LogServiceByExecutorService() {
        this.queue = new LinkedBlockingQueue<>(100);
        this.taskExec = Executors.newSingleThreadExecutor();
        // 需要将buffer 刷出，否则会丢失
        this.writer = new PrintWriter(System.out, true);
    }

    // 服务停止
    public void stop() throws InterruptedException {
        try {
            this.taskExec.shutdown();
            this.taskExec.awaitTermination(3, TimeUnit.MINUTES);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) {
        taskExec.execute(new WriteTask(msg));
    }

    // 执行服务获取
    public ExecutorService getTaskExec() {
        return taskExec;
    }

    // 写日志任务
    private class WriteTask implements Runnable {

        final String msg;

        WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            LogServiceByExecutorService.this.writer.println(msg);
            // 模拟2秒写文件
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
