package cn.gyw.corejava.concurrent.demo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 不可靠的生产者-消费者日志服务
 *
 * @see LogService
 */
public class LogWriter {

    private final static int CAPACITY = 100;

    private BlockingQueue<String> queue;
    private LoggerThread logger;

    private volatile boolean shutdownRequested = false;

    public LogWriter() {
        this.queue = new LinkedBlockingQueue<>(CAPACITY);
        this.logger = new LoggerThread(new PrintWriter(System.out));
    }

    public void start() {
        this.logger.start();
    }

    /**
     * 通过一种不可靠的方式为日志服务增加关闭支持
     *
     * 可以通知生产者线程，服务已经关闭
     *
     * 问题：
     * 1）存在竞态条件，不可靠，一种“先判断再运行”的代码序列，
     * 生产者发现服务还未关闭，会在服务未关闭时，进行put从而阻塞，无法解除阻塞状态
     * （可以在队列宣布清空之前，消费者等待数秒钟，降低这种风险概率）
     * @param msg
     * @throws InterruptedException
     */
    public void log2(String msg) throws InterruptedException {
        if (!shutdownRequested) {
            queue.put(msg);
        } else {
            throw new IllegalStateException("logger is shutdown");
        }
    }

    /**
     * 存在问题
     * 1）如果生产者远快于消费者，生产者会阻塞
     * 2）若服务停止，消费者线程会取消，等待队列中的任务会丢失
     * 3）那些被阻塞的生产者线程，无法解除阻塞状态，由于生产者并不是专门的线程，取消很困难
     * @param msg
     * @throws InterruptedException
     */
    public void log1(String msg) throws InterruptedException {
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
                    String log = LogWriter.this.queue.take();
                    writer.println(log);
                }
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }


        }
    }
}
