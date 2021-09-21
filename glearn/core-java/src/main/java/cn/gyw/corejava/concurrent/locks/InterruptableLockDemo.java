package cn.gyw.corejava.concurrent.locks;

/**
 * 中断锁
 * <p>
 * Created by guanyw on 2019/1/24.
 */
public class InterruptableLockDemo {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread());
                try {
                    for (int i = 0; i < 5000000; i++) {
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println("should be stopped and exit");
                            throw new InterruptedException();
                            // run 方法会被执行完
//                        break;
                        }
                        System.out.println("i = " + i);
                    }
                    System.out.println("this line also executed");
                } catch (InterruptedException e) {
                    // 抛出异常的方法，中断线程继续执行
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("before 中断状态：" + thread.isInterrupted());

        // 线程进入阻塞状态（sleep, wait, join, I/O 阻塞），会清除中断状态
/*        try {
//            thread.sleep(1000);
//            thread.join();
//            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 请求中断线程Thread，线程只是结束循环，方法还是执行完了，没有立即终止
        thread.interrupt();

        System.out.println("after 中断状态：" + thread.isInterrupted());
    }
}
