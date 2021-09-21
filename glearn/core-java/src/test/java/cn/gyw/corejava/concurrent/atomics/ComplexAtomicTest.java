package cn.gyw.corejava.concurrent.atomics;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 复合变量类
 */
public class ComplexAtomicTest {

    @Test
    public void atomicMarkable() {
        AtomicMarkableReference<String> aba = new AtomicMarkableReference<>("A", false);
        aba.compareAndSet("A", "B", false, false);

        System.out.println(aba.getReference());
    }

    @Test
    public void atomicStamped() {
        AtomicStampedReference<String> aba = new AtomicStampedReference<>("A", 0);

        aba.set("B", 1);
        aba.set("A", 0);

        aba.compareAndSet("A", "C", 1, 2);

        System.out.println(aba.getReference());
    }
}
