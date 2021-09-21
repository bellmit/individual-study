package cn.gyw.corejava.concurrent.locks;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 使用条件队列实现阻塞有界缓存
 *
 * @param <V>
 */
@ThreadSafe
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    // 阻塞并直到not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }

        // 条件通知
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }

    // 阻塞并直到not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
