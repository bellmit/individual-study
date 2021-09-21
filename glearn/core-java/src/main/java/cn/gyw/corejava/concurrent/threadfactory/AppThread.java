package cn.gyw.corejava.concurrent.threadfactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 定制线程
 *
 * 1)为线程指定名称
 * 2）设置自定义UncaghtExceptionHandler 写入日志信息
 * 3）维护统计信息（多少线程创建和销毁）
 * 4）线程创建或销毁时记入日志
 */
public class AppThread extends Thread {

    private final static String DEFAULT_NAME = "GywThread";
    private volatile static boolean debugLifecycle = false;
    private final static AtomicInteger created = new AtomicInteger();
    private final static AtomicInteger alive = new AtomicInteger();
    private final static Logger log = Logger.getAnonymousLogger();

    public AppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public AppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.log(Level.SEVERE, "UNCAGHT in thread " + t.getName(), e);
            }
        });
    }

    @Override
    public void run() {
        // 复制debug 标志以确保一致的值
        boolean debug = debugLifecycle;

        if (debug) {
            log.log(Level.FINE, "created " + getName());
        }

        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
                log.log(Level.FINE, "exiting " + getName());
            }
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebugLifecycle(boolean b) {
        debugLifecycle = b;
    }
}
