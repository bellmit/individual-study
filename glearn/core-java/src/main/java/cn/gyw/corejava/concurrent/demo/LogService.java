package cn.gyw.corejava.concurrent.demo;

import cn.gyw.platform.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * 日志服务
 * 可靠的取消操作
 *
 * @see LogWriter
 */
public class LogService {

    private BlockingQueue<String> queue;
    private LoggerThread logger;

    @GuardedBy("this")
    private boolean isShutdown;

    // 计数器“保持”提交消息的权利
    @GuardedBy("this")
    private int reservations;

    public void start() {
        this.logger.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        logger.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("logger is shutdown");
            }
            // 计数：新增了一条日志
            ++ reservations;
        }
        queue.put(msg);
    }


    // 写日志线程
    private class LoggerThread extends Thread {

        final PrintWriter writer;

        // final BlockingQueue<String> queue;

        LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {

            try {
                while (true){
                    try {
                        synchronized (LogService.this) {
                            // 关闭且等待处理数为0，结束消费线程
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = LogService.this.queue.take();
                        synchronized (LogService.this) {
                            -- reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
