package cn.gyw.corejava.concurrent.task;

/**
 * Created by guanyw on 2018/12/19.
 */
public class PrintStringTask implements Task{

    // 非线程限制
    private String srcStr;

    // 线程限制
//    private ThreadLocal<String> srcStr;

    public PrintStringTask(String str) {
        this.srcStr = str;
    }

    @Override
    public void execute() {
        try {
            Thread.sleep((int)Math.random() * 3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + "当前字符串：" + srcStr);
    }
}
