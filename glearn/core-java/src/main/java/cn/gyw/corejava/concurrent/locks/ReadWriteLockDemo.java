package cn.gyw.corejava.concurrent.locks;

import cn.gyw.corejava.concurrent.task.CounterTask;

/**
 * 读写锁
 * <p>
 * Created by guanyw on 2019/1/24.
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        // 2 个线程写
        CounterTask task = new CounterTask();
        new Thread(() -> {
            task.setNumber(10);
        }, "Thread-w-1").start();
        new Thread(() -> {
            task.setNumber(12);
        }, "Thread-w-2").start();
        // 10 个线程读
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {task.getNumber();}, "Thread-r-" + (i + 1)).start();
        }

    }
}
