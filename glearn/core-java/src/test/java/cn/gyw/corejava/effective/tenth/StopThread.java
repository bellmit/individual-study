package cn.gyw.corejava.effective.tenth;

import java.util.concurrent.TimeUnit;

public class StopThread {

//    private static boolean stopRequested; // 死循环

    private volatile static boolean stopRequested; // 可见性

    public static void main(String[] args) throws InterruptedException {
        Thread backgroudThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested) {
                    i ++;
                }
            }
        });

        backgroudThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
