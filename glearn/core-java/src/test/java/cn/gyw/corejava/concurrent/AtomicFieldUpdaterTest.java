package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicFieldUpdaterTest {

    public static AtomicIntegerFieldUpdater<AtomicFieldUpdaterTest> updater
            = AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUpdaterTest.class, "count");

    // 非静态的
    public volatile int count = 100;

    private static AtomicFieldUpdaterTest updaterTest = new AtomicFieldUpdaterTest();

    @Test
    public void base() {
        if (updater.compareAndSet(updaterTest, 100, 120)) {
            System.out.println("success...count:" + updaterTest.getCount());
        }

        if (updater.compareAndSet(updaterTest, 100, 130)) {
            System.out.println("success...count:" + updaterTest.getCount());
        } else {
            System.out.println("failed...count:" + updaterTest.getCount());
        }
    }

    public int getCount() {
        return count;
    }
}
