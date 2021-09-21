package cn.gyw.corejava.concurrent.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 *
 * Created by guanyw on 2018/12/14.
 */
public final class CustomThreadPoolFactory {

    public static ExecutorService newCustomThreadPool() {

        return new ThreadPoolExecutor(
                /*
                 corePoolSize : 核心线程池大小，在线程池被创建之后，其实里面没有线程（调用prestartAllCoreThreads()
                 或者prestartCoreThread()方法会预创建线程，而不用等着任务的到来）。当有任务过来才会创建线程执行；
                 当线程池中的线程达到corePoolSize的大小，则将任务放入缓冲队列中
                  */
                2,
                /*
                 maximumPoolSize：最大线程数量是多少。标志着线程池最大线程数量
                  */
                20,
                /*
                keepAliveTime：当线程没有任务时，最多保持的时间，超过这个时间就被终止了;
                默认情况下，只有 线程池中线程数量 大于 corePoolSize时，keepAliveTime值才会起作用。
                也就是，只有在线程池线程数量超出corePoolSize了，才会把超时的空闲线程给停止掉，否则保持线程池中有 corePoolSize个线程
                 */
                60,
                /*
                参数keepAliveTime的时间单位
                 */
                TimeUnit.SECONDS,
                /*
                workQueue：用来存储待执行任务的队列，不同的线程池的队列实现方式不同
                1.ArrayBlockingQueue：基于数组的队列，有界队列，创建时需要指定大小。
                2.LinkedBlockingQueue：基于链表的队列，无界队列，如果没有指定大小，则默认值是 Integer.MAX_VALUE。（newFixedThreadPool和newSingleThreadExecutor使用的就是这种队列）。
                3.SynchronousQueue：不排队就直接创建新线程提交任务。（newCachedThreadPool使用的就是这种队列）
                 */
                new SynchronousQueue<Runnable>(),
                /*
                threadFactory:线程工厂，用来创建线程
                 */
                new CustomThreadFactory(),
                /*
                Handler：拒绝执行任务时的策略
                一般来讲有以下四种策略，
               （1） ThreadPoolExecutor.AbortPolicy 丢弃任务，并抛出 RejectedExecutionException 异常。
               （2） ThreadPoolExecutor.CallerRunsPolicy：该任务被线程池拒绝，由调用 execute方法的线程执行该任务。
               （3） ThreadPoolExecutor.DiscardOldestPolicy ： 抛弃队列最前面的任务，然后重新尝试执行任务。
               （4） ThreadPoolExecutor.DiscardPolicy，丢弃任务，不过也不抛出异常。
                 */
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "自定义线程池-" + count.getAndIncrement());
        }
    }
}
