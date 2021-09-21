package cn.gyw.corejava.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {

    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("wait singal ..");  // 1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("get singal .."); // 4
                lock.unlock();
            }
        }).start();


        new Thread(() -> {
            lock.lock();
            System.out.println("get locks"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            System.out.println("send singal ..");  // 3
            lock.unlock();
        }).start();
    }
}
