package cn.gyw.corejava.concurrent.cache;

import cn.gyw.corejava.concurrent.cache.annotations.Computable;
import cn.gyw.corejava.exceptions.ExUtil;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 构建高效且可伸缩的缓存
 * @param <A>
 * @param <V>
 *
 * 方式三：高效、避免重复计算
 *
 * 优点：
 * 1）判断一个计算是否已经开始，如果未开始，则创建一个FutureTask并注册到map中，启动计算；
 * 2）如果已经启动，则等待计算结果；
 *
 * 安全漏洞：line 40
 */
public class Memoizer3<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * HashMap 非线程安全，所以需要同步保护
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) { // 非线程安全，非原子操作
            FutureTask<V> task = new FutureTask<>(() -> {
                return c.compute(arg);
            });
            f = task;
            cache.put(arg, f);
            task.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw ExUtil.launderThrowable(e.getCause());
        }
    }
}
