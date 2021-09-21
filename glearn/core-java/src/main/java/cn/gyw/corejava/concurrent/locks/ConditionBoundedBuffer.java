package cn.gyw.corejava.concurrent.locks;

import cn.gyw.platform.annotations.GuardedBy;
import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class ConditionBoundedBuffer<T> {

    protected final Lock lock = new ReentrantLock();

    // 条件谓词 notFull -> (count < items.length)
    private final Condition notFull = lock.newCondition();

    // 条件谓词 notEmpty -> (count > 0)
    private final Condition notEmpty = lock.newCondition();

    @GuardedBy("lock")
    private final T[] items = (T[]) new Object[10];

    @GuardedBy("lock")
    private int tail,head,count;

    // 阻塞直到 notFull
    public void put(T t) throws InterruptedException {
        lock.lock();

        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = t;
            if (++tail == items.length) {
                tail = 0;
            }
            ++ count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // 阻塞直到 notEmpty
    public T take() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            if (++head == items.length) {
                head = 0;
            }
            -- count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }

    }

}
