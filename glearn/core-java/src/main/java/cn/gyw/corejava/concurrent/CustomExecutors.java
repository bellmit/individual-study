package cn.gyw.corejava.concurrent;

import cn.gyw.corejava.concurrent.threadfactory.MyThreadFactory;
import cn.gyw.corejava.concurrent.threadpool.TimingThreadPool;
import cn.gyw.corejava.util.SystemUtil;

import java.util.concurrent.*;

/**
 * 自定义线程池工具类
 */
public final class CustomExecutors {

    private static final int CAPACITY = 64;

    /**
     * 有计算时间的线程池
     * @return
     */
    public static ExecutorService newTimingThreadPool() {
        return new TimingThreadPool();
    }

    /**
     * 不可修改的线程池
     *
     * @return
     */
    public static ExecutorService unmodifiedPool() {
        return Executors.unconfigurableExecutorService(
                Executors.newFixedThreadPool(SystemUtil.getCpuCnt() + 1));
    }

    /**
     * 使用调用者执行的饱和策略线程池
     *
     * @return
     */
    public static ExecutorService newFixedPoolByRunnerCallers(int nthreads) {
        return new ThreadPoolExecutor(nthreads, nthreads,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(CAPACITY),
                new MyThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    private CustomExecutors() {
    }
}
