package cn.gyw.corejava.exceptions;

/**
 * 未捕获异常全局处理器
 */
public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    // 打印异常日志
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("server terminated with exception:" + t.getName());
    }
}
