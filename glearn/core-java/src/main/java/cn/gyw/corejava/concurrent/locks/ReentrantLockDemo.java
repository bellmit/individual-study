package cn.gyw.corejava.concurrent.locks;

import cn.gyw.corejava.concurrent.task.CounterTask;
import cn.gyw.corejava.concurrent.task.Task;
import cn.gyw.corejava.concurrent.task.processor.TaskProcess;
import cn.gyw.corejava.concurrent.thread.pool.CustomThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * 可重入锁：java.support.concurrent.locks.ReentrantLock 学习
 * why:
 * 1)父类和子类的方法都是加锁的,子类和父类都将获取父类锁，子类调用父类方法，需要获取父类锁，
 * 锁可重入，所以不会死锁，否则发生死锁，子类已经获取到父类锁，再去调父类方法，获取父类锁，阻塞；
 * 2)递归调用，获取锁
 *
 * 新的特性
 * 优点：
 * 1）可中断锁
 * 2）超时锁等待
 * 3) 公平锁
 * 4）读写锁
 * 缺点：
 *
 * Created by guanyw on 2019/1/23.
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        ExecutorService executorService = CustomThreadPoolFactory.newCustomThreadPool();

        demo.testInterruptLockWait(executorService);

        executorService.shutdown();
    }

    /**
     * 锁可中断
     *
     * @param executorService
     */
    private void testInterruptLockWait(ExecutorService executorService) {
        Task task = new CounterTask();
        executorService.submit(new TaskProcess(task));
        executorService.submit(new TaskProcess(task));
        executorService.submit(new TaskProcess(task));
        executorService.submit(new TaskProcess(task));
        executorService.submit(new TaskProcess(task));
    }

}
