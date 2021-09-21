package cn.gyw.corejava.concurrent.threadfactory;

import java.util.concurrent.ThreadFactory;

/**
 * 定制的线程工厂
 */
public class MyThreadFactory implements ThreadFactory {

    private final static String POOL_NAME = "GYW";

    @Override
    public Thread newThread(Runnable r) {
        return new AppThread(r, POOL_NAME);
    }
}
