package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public AtomicReference<Integer> count = new AtomicReference<>(0);

    @Test
    public void base() {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);

        System.out.println("count :" + count.get());
    }
}
