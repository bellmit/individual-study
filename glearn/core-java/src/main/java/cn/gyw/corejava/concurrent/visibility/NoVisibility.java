package cn.gyw.corejava.concurrent.visibility;

/**
 * 在 没有同步的情况下共享变量
 *
 * 主线程和读线程访问共享变量ready和number。
 * 主线程启动读线程，然后把number设置成42，ready赋值为true
 * 读线程进行循环，知道发现ready的值为true，打印出number的值
 *
 * 结果可能：42，0 ， 永不停止(发生)
 */
public class NoVisibility {

    // volatile的典型应用：当作标识完成、中断、状态的标记使用
    private static volatile boolean ready = false;

    private static int number;

    public static void main(String[] args) {
        new ReadThread().start();
        number = 42;
        ready = true;
    }

    private static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                    Thread.yield();
                }
                System.out.println(number);
        }
    }
}
