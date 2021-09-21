package cn.gyw.corejava.concurrent.cache;

import cn.gyw.corejava.concurrent.cache.annotations.Computable;
import cn.gyw.corejava.exceptions.ExUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 构建高效且可伸缩的缓存
 * @param <A>
 * @param <V>
 *
 * 方式四：高效、避免重复计算
 *
 * 优点：
 * 1）判断一个计算是否已经开始，如果未开始，则创建一个FutureTask并注册到map中，启动计算；
 * 2）如果已经启动，则等待计算结果；
 *
 * 缺点：
 * 1）缓存污染：如果某个计算被取消或者失败，那么计算这个结果时将指明计算过程被取消或失败；
 * （如果发现计算被取消，将Future 从缓存中移除；如果RuntimeException，也从缓存中移除；那么将来的计算才能成功）
 * 2）缓存逾期：可以通过FutureTask的子类来解决，在子类中，为每个结果指定逾期的时间，并定期扫描缓存中逾期的元素；
 * 3）缓存清理：移除旧的计算结果以便为新的计算腾出空间，从而使缓存不会消耗过多内存；
 */
public class Memoizer4<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memoizer4(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                FutureTask<V> task = new FutureTask<>(() -> {
                    return c.compute(arg);
                });

                f = cache.putIfAbsent(arg, task);
                if (f == null) {
                    f = task;
                    task.run();
                }
            }
            try {
                return f.get();
            } catch (ExecutionException e) {
                throw ExUtil.launderThrowable(e.getCause());
            }
        }
    }
}
