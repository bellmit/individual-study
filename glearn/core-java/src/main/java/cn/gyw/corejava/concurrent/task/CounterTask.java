package cn.gyw.corejava.concurrent.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 统计器任务
 * <p>
 * Created by guanyw on 2019/1/24.
 */
public class CounterTask implements Task {

    private Lock lock = new ReentrantLock();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private int number;

    private int counter;

    @Override
    public void execute() {
        // countAdd();
//        countAdd1();
         countAdd2();
    }

    /**
     * 非线程安全
     */
    public void countAdd() {
        before();
        counter++;
        after();
    }

    /**
     * 可重入读写锁
     *
     * 写锁（互斥锁）
     */
    public void setNumber(int number) {
        System.out.println(Thread.currentThread().getName() + " 获取写锁");
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + number);
            this.number = number;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读锁（共享锁）
     */
    public void getNumber() {
        System.out.println(Thread.currentThread().getName() + " 获取读锁");
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + number);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

     /**
     * 超时锁等待
     */
    public void countAdd2() {
        System.out.println(lock);
        try {
            /*
            1)在超时时间内，当前线程成功获取了锁
            2)当前线程在超时时间内被中断
            3)超时时间结束，仍未获得锁返回false
             */
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                before();
                counter++;
                Thread.sleep(3000);
                after();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 可中断锁
     */
    public void countAdd1() {

        try {
            lock.lock();
            // 设置可中断性
            lock.lockInterruptibly();
            before();
            if (counter == 3) {
                // Thread 类中断方法，进行中断
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + ",进行睡眠前，中断状态：" + Thread.currentThread().isInterrupted());
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ",进行睡眠后，中断状态：" + Thread.currentThread().isInterrupted());
            }

            counter++;
            after();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 必须释放锁
            lock.unlock();
        }
    }

    private void before() {
        System.out.println(Thread.currentThread().getName() + ", counter from : " + counter);
    }

    private void after() {
        System.out.println(Thread.currentThread().getName() + ", counter to : " + counter);
    }

}
