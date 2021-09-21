package cn.gyw.corejava.concurrent.locks;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 将前提条件的失败传递给调用者
 */
@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new RuntimeException("space is full");
        }
        doPut(v);
    }

    /**
     * 获取失败
     * @return
     */
    public synchronized V take() {
        if (isEmpty()) {
            throw new RuntimeException("space is empty");
        }
        return doTake();
    }
}
