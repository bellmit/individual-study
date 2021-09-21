package cn.gyw.corejava.concurrent.task.processor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 回环栅栏的任务
 *
 * 多个线程写完之后才可以进行读取
 */
public class CyclicBarrierProcess implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierProcess(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] 正在写入数据");
        try {
            // 模拟写入操作
            Thread.sleep(5000);
            System.out.println("Thread [" + Thread.currentThread().getName() + "] 写入数据完成");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Thread [" + Thread.currentThread().getName() + "] 离开，进行其他操作");
    }
}
