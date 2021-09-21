package cn.gyw.corejava.concurrent.locks;

import cn.gyw.corejava.concurrent.thread.pool.CustomThreadPoolFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * SemaphoreDemo 信号量机制
 *
 * 模拟银行存钱
 * 1）一个银行
 * 2）两个柜台
 * 业务：柜台空，存取钱；柜台不空，等待；
 *
 * Created by guanyw on 2019/1/25.
 */
public class SemaphoreDemo {

    private int i;

    public static void main(String[] args) {
        SemaphoreDemo demo = new SemaphoreDemo();
        Bank bank = demo.new Bank();
        // 定义信号量
        Semaphore semaphore = new Semaphore(2);
        ExecutorService executorService = CustomThreadPoolFactory.newCustomThreadPool();
        for (int i = 0 ; i < 10 ; i ++) {
            executorService.submit(demo.new MoneySaveThread(bank, semaphore));
        }
        executorService.shutdown();

        // 从信号量中获取两个许可，并且在获得许可之前，一直将线程阻塞
        semaphore.acquireUninterruptibly(2);
        System.out.println("到点了，工作人员要吃饭了");
        // 释放两个许可，并将其返回给信号量
        semaphore.release(2);
    }

    /**
     * 银行类
     */
    class Bank {

        private int acount;

        public int getAcount() {
            return acount;
        }

        // 存钱
        public void save(int money) {
            this.acount += money;
        }
    }

    /**
     * 存钱任务
     */
    class MoneySaveThread implements Runnable {

        private Bank bank;

        private Semaphore semaphore;

        public MoneySaveThread(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // 信号量许可
                semaphore.acquire();
                int count = i++;
                printWithThread(semaphore.availablePermits() + ", 进入银行柜台，存钱");
                bank.save(10);
                printWithThread(count +"账户余额为：" + bank.getAcount());
                Thread.sleep(1000);
                printWithThread("存钱完毕，离开银行");
                // 信号量释放
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void printWithThread(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append(Thread.currentThread().getName())
                .append(":")
                .append(obj);
        System.out.println(builder.toString());
    }
}