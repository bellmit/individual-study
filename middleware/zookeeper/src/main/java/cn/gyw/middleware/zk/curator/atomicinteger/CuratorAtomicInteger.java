package cn.gyw.middleware.zk.curator.atomicinteger;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * 分布式计数器
 */
public class CuratorAtomicInteger {

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

    public static void main(String[] args) throws Exception {
        CuratorFramework cf = createCuratorFramework();

        cf.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(cf, "/counter",
                new RetryNTimes(3, 1000));

//        atomicInteger.forceSet(0);
        AtomicValue<Integer> value = atomicInteger.get();
        // 增加
        atomicInteger.increment();
        // 减少
        atomicInteger.decrement();

        System.out.println(value.succeeded());

        // 最新值
        System.out.println(value.postValue());
        // 原始值
        System.out.println(value.preValue());

    }

}
