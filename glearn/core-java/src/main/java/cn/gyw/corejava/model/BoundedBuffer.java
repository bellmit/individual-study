package cn.gyw.corejava.model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 条件变量实现 阻塞式Buffer
 *
 * 对象数组
 *
 * 1）take ：数组为空，线程等待；
 * 2）put ：数组满，线程等待；
 *
 * Created by guanyw on 2019/1/25.
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();

    // 条件：不满
    final Condition notFull = lock.newCondition();

    // 条件：非空
    final Condition notEmpty = lock.newCondition();

    private final int default_capacity = 10;

    private final Object[] buffer;

    // 数组容量
    private int capacity;

    // 数组角标
    private int curIndex = 0;
    private int curTake = 0;

    // 实际元素数
    private int size = 0;

    public BoundedBuffer() {
        capacity = default_capacity;
        buffer = new Object[capacity];
    }

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        buffer = new Object[capacity];
    }

    /**
     * 增加操作
     *
     * @param obj
     */
    public void put(Object obj) {
        lock.lock();
        try{
            if (buffer.length == size) {
                // 数组满，等待非满条件
                notFull.await();
            }
            buffer[curIndex] = obj;
            curIndex = (++curIndex) % capacity;
            size++;
            // 通知非空条件
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查询操作
     *
     * @return
     */
    public Object take() {
        lock.lock();
        Object result = null;
        try {
            if (size == 0) {
                // 等待非空条件
                notEmpty.await();
            }
            result = buffer[curTake];
            curTake = (++curTake) % capacity;
            size--;
            // 通知非满条件
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

}
