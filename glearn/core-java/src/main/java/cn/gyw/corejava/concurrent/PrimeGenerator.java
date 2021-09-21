package cn.gyw.corejava.concurrent;

import cn.gyw.platform.annotations.GuardedBy;
import cn.gyw.platform.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 素数生成器
 *
 * 使用volatile 关键字来保存取消状态
 */
@ThreadSafe
public class PrimeGenerator implements Runnable {

    private volatile boolean cancelled;

    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    // 取消任务
    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> getPrimes() {
        // 新对象
        return new ArrayList<>(primes);
    }
}
