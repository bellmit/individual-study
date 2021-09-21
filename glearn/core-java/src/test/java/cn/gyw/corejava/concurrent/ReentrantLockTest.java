package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantLock
 */
public class ReentrantLockTest extends BaseCondition {

    private int count = 0;

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private Map<String, Data> map = new TreeMap<>();
    private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock readLock = rwlock.readLock();
    private final Lock writeLock = rwlock.writeLock();

    /**
     * locks 基本用法
     */
    @Test
    public void base() {
        lock.lock();
        try {
            System.out.println("do other things ...");
        } finally {
            lock.unlock();
        }
        System.out.println("success");
    }

    @Override
    protected void buildTask(Integer index) {
        // locks.locks();
        if (lock.tryLock()) {
            try {
                count++;
            } finally {
                lock.unlock();
            }
        } else {
            // do something other
        }
    }

    @Override
    protected void printInfo() {
        System.out.println(">> " + count);
    }

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.work();
    }

    public Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public Data put(String key, Data value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    class Data {

    }
}
