package cn.gyw.corejava.concurrent.cache.computes;

import cn.gyw.corejava.concurrent.cache.annotations.Computable;

import java.math.BigInteger;

/**
 * 高开销的计算
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // 经过长时间的计算
        return new BigInteger(arg);
    }

}
