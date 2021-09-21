package cn.gyw.corejava.concurrent.cache;

import cn.gyw.corejava.concurrent.cache.annotations.Computable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 构建高效且可伸缩的缓存
 * @param <A>
 * @param <V>
 *
 * 方式二：方法不需要同步，使用线程安全的并发容器
 * 缺点：由于方法正在计算时，其他线程不可知，那么可能会导致多个线程计算相同的结果
 * 这与缓存机制来说，很糟糕；
 * 对于提供单次初始化对象缓存来说，2次初始化可能导致安全问题
 */
public class Memoizer2<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
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
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
