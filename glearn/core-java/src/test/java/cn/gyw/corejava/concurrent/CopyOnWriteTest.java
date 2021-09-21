package cn.gyw.corejava.concurrent;

import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 写时复制
 */
public class CopyOnWriteTest {


    /**
     * CopyOnWriteArrayList 遍历测试
     */
    @Test
    public void list() {
        String[] datas = new String[]{"aa", "bb", "cc"};
        final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(datas);

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(3);

        for (int i = 0 ; i < 2 ; i ++) {
            new Thread(new IteratorList(list, startLatch), "name-" + i).start();
            endLatch.countDown();
        }

        new Thread(() -> {
            try {
                startLatch.await();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("add element dd");
//            list.add("DD");
            list.set(0, "DD");
            endLatch.countDown();
        }).start();

        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IteratorList implements Runnable {

        private CopyOnWriteArrayList<String> data;
        private CountDownLatch latch;

        IteratorList(CopyOnWriteArrayList<String> list, CountDownLatch latch) {
            this.data = list;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Iterator<String> iter = data.iterator(); iter.hasNext() ; ) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 1s sleep >>" + iter.next());
            }
        }
    }
}
