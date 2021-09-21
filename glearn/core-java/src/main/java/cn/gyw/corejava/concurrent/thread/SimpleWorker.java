package cn.gyw.corejava.concurrent.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 简单的worker线程
 */
public class SimpleWorker extends Thread {

    private CountDownLatch countDownLatch;

    public SimpleWorker(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            this.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
