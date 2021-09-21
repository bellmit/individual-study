package cn.gyw.corejava.concurrent.locks;

import cn.gyw.platform.annotations.ThreadSafe;

/**
 * 可重新关闭的阀门
 */
@ThreadSafe
public class ThreadGate {

    private boolean isOpen;
    private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++ generation;
        isOpen = true;
        notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) {
            wait();
        }
    }

}
