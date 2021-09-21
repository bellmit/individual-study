package cn.gyw.corejava.concurrent.atomics;

import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS来维持包含多个变量的不变性条件
 */
@ThreadSafe
public class CasNumberRange {

    // 原子引用
    private AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0,0));

    public int getLower() {
        return values.get().getLower();
    }

    public int getUpper() {
        return values.get().getUpper();
    }

    public void setLower(int i) {
        while (true) { // 自旋
            IntPair old = values.get();
            if (old.getUpper() < i) { // 不变性条件
                throw new IllegalArgumentException("can not set lower =" + i + " > upper");
            }
            IntPair newPair = new IntPair(i, old.getUpper());
            if (values.compareAndSet(old, newPair)) {
                return;
            }
        }
    }

    public void setUpper(int i) {
        while (true) {
            IntPair old = values.get();
            if (old.getLower() > i) { // 不变性条件
                throw new IllegalArgumentException("can not set upper =" + i + " < lower");
            }
            IntPair newPair = new IntPair(old.getLower(), i);
            if (values.compareAndSet(old, newPair)) {
                return;
            }
        }
    }


    // 区间
    private class IntPair {
        // 不变性条件 lower <= upper
        final int lower;
        final int upper;

        IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public int getLower() {
            return lower;
        }

        public int getUpper() {
            return upper;
        }
    }
}
