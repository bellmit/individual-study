package cn.gyw.glearn.algorithm.interview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程顺序执行
 *
 * 1.join
 * 2.队列
 *
 */
public class ExecuteInOrder {

    static Thread thread1 = new Thread(()->{
        System.out.println("Thread-1");
    }, "Thread-1");

    static Thread thread2 = new Thread(()->{
        System.out.println("Thread-2");
    }, "Thread-2");

    static Thread thread3 = new Thread(()->{
        System.out.println("Thread-3");
    }, "Thread-3");

    static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

        service.submit(thread1);
        service.submit(thread2);
        service.submit(thread3);
        service.shutdown();

        /*try {
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
            thread3.start();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
