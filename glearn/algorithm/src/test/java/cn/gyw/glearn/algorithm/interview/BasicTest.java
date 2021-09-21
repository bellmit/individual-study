package cn.gyw.glearn.algorithm.interview;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * 基础知识测试
 */

public class BasicTest {


    @Test
    public void tryCatchReturn() {
        int rest = doRunError();
        System.out.println(">>" + rest);
    }

    private int doRunError() {
        int result = 10;
        try {
            int i = 10 / 0;
            return result;
        } catch (Exception e) {
            result = 11;
            return result;
        } finally {
            result = 12;
        }
    }

    @Test
    public void objectWait() {
        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("wait 3s" + LocalDateTime.now());
                    lock.wait(3000);
                    System.out.println("wake up"+ LocalDateTime.now());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadTest() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call invoke ...");
                return "call";
            }
        };
        RunnableFuture<String> rf = new FutureTask<>(callable);

        Thread thread = new Thread(rf);

        thread.start();

        try {
            String result = rf.get();
            System.out.println("result>>" + result);
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queue() {
        Queue<String> queue = new ArrayBlockingQueue<>(10);

        System.out.println(queue.poll());
        queue.remove();
    }

    @Test
    public void collects() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add(null);
//
//        HashSet<String> hashSet1 = new HashSet<>();
//        hashSet1.add(null);
//
//        TreeSet<String> set2 = new TreeSet<>();
//        set2.add(null);

        TreeMap<String, String> map1 = new TreeMap<>();
        map1.put(null, null);

    }

    /**
     * 字符串反转
     */
    @Test
    public void stringReverse() {
        String str = "abcdefg";
//        String result = new StringBuilder(str).reverse().toString();
        String result = new StringBuffer(str).reverse().toString();

        System.out.println(">>" + result);
    }

    /**
     * hashCode 相等，对象不相等
     */
    @Test
    public void hashCodeEquals() {
        String a = "通话";
        String b = "重地";

        System.out.println("a >" + a.hashCode());
        System.out.println("b >" + b.hashCode());
    }

    @Test
    public void equalsTest() {
        int i = 10;

    }
}
