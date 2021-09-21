package cn.gyw.corejava.concurrent.locks;

import java.util.concurrent.TimeUnit;

/**
 * 使用简单阻塞的有界缓冲
 *
 * 自旋+休眠实现
 * @param <V>
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            // 休眠
            TimeUnit.SECONDS.sleep(10);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            // 休眠
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
