package cn.gyw.corejava.concurrent.cache;

import cn.gyw.corejava.concurrent.cache.annotations.Computable;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建高效且可伸缩的缓存
 *
 * 目的：用于改进一个高计算开销的函数
 *
 * 方式一：通过hashmap 保存之前的结果
 * 缺点：方法同步，导致串行化，高消耗的操作可能比想象中的长
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new HashMap<>();

    // 不需要synchronized，但有安全漏洞
    // private final Map<JolObject, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * HashMap 非线程安全，所以需要同步保护
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
