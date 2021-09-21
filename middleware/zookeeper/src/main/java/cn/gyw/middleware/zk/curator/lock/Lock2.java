package cn.gyw.middleware.zk.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式系统实现数据一致性
 * <p>
 * 通过第三方工具：Zookeeper curator
 */
public class Lock2 {

    /**
     * zookeeper地址
     */
    static final String CONNECT_ADDR = "192.168.1.181:2181,192.168.1.182:2181,192.168.1.183:2181";

    public static CuratorFramework createCuratorFramework() {
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(60)
                .retryPolicy(retryPolicy)
                .build();
        return cf;
    }

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                CuratorFramework cf = createCuratorFramework();
                cf.start();
                final InterProcessMutex lock = new InterProcessMutex(cf, "/lock");
                // 分布式环境，类似创建了多个局部的ReentrantLock
//                final ReentrantLock reentrantLock = new ReentrantLock();
                try {
                    countDownLatch.await();
                    lock.acquire();
//                    reentrantLock.lock();
                    System.out.println(Thread.currentThread().getName() + "执行业务逻辑...");
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 释放
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "t" + i).start();
        }

        countDownLatch.countDown();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
