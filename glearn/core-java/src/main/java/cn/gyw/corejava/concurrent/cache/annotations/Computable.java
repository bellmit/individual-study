package cn.gyw.corejava.concurrent.cache.annotations;

/**
 *
 * @param <A> 输入类型
 * @param <V> 输出类型
 */
public interface Computable<A, V> {

    V compute(A arg) throws InterruptedException;
}
