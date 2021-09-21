package cn.gyw.corejava.concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS ABA 问题
 */
public class AtomicStampReferenceTest {

    private AtomicStampedReference<Integer> stampedReference
            = new AtomicStampedReference<>(0, 0);
}
