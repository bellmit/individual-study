package cn.gyw.corejava.concurrent.atomics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于AtomicInteger 实现的随机数生成器
 */
public class AtomicPseudoRandom {

    private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            // 新增下一个
            int nextSeed = (++s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

}
