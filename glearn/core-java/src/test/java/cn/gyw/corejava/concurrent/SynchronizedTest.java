package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {

    private void testCodeBlock(int j) {
        synchronized (this) {
            for (int i = 0 ; i < 10; i ++) {
                System.out.println("j >" + j + " : i >" + i);
            }
        }
    }

    public synchronized void testSyncStaticMethod(int j) {
        for (int i = 0 ; i < 10; i ++) {
            System.out.println("j >" + j + " : i >" + i);
        }
    }

    @Test
    public void codeBlock() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 模拟两个线程同时访问代码块
        executorService.execute(() -> {
            testCodeBlock(1);
        });
        executorService.execute(() -> {
            testCodeBlock(2);
        });

        System.in.read();
    }
}
