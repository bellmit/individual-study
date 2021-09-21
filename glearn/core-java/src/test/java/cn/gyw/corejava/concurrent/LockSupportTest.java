package cn.gyw.corejava.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 *
 */
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("t1-------");
                LockSupport.park();

            }
        });

        t1.start();

        Thread.sleep(1000);

        LockSupport.unpark(t1);
    }
}
