package cn.gyw.corejava.concurrent.demo.puzzle;

import cn.gyw.platform.annotations.GuardedBy;
import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 结果闭锁
 */
@ThreadSafe
public class ValueLatch<T> {

    @GuardedBy("this")
    private T value = null;

    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }

    /**
     * 指定时间获取结果
     * @param timeout
     * @param unit
     * @return
     */
    public T getValue(long timeout, TimeUnit unit) throws InterruptedException {
        done.await(timeout, unit);
        synchronized (this) {
            return null;
        }
    }

}
