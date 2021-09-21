package cn.gyw.corejava.concurrent.locks;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 锁模板
 * Created by guanyw on 2018/7/12.
 */
public abstract class AbstractLockTemplate {

    private String id;

    protected int round;

    private int threadNum;

    protected long conutValue;

    protected int[] preInit;

    protected int index;

    Random r = new Random(47);

    // Barrir ：任务栅栏，先将任务挂起，达到配置的任务数，才能往下执行
    private CyclicBarrier barrier;

    public AbstractLockTemplate(String id, int threadNum, CyclicBarrier barrier) {
        this.id = id;
        this.threadNum = threadNum;
        this.barrier = barrier;
    }

    public AbstractLockTemplate(String id, int round, int threadNum, CyclicBarrier barrier) {
        this.id = id;
        this.round = round;
        this.threadNum = threadNum;
        this.barrier = barrier;
    }

    abstract void sumValue();

    /**
     * 对long的操作时非原子的，原子操作只针对32位
     * long是64位，底层操作的时候分2个32位读写，因此不是线程安全的
     *
     * @return
     */
    abstract long getValue();

    public void testTime() {
        ExecutorService service = Executors.newCachedThreadPool();
        long start = System.nanoTime();

        // 同时开启2倍的ThreadNum个数的读写线程
        for (int i = 0; i < threadNum; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < round; i++) {
                        sumValue();
                    }

                    // 每个线程执行完同步方法后就等待
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });

            service.execute(new Runnable() {
                @Override
                public void run() {
                    getValue();

                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        try {
            //当前统计线程也wait,所以CyclicBarrier的初始值是threadNum*2+1
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        long duration = System.nanoTime() - start;
        System.out.println(id + " = " + duration);
    }
}
