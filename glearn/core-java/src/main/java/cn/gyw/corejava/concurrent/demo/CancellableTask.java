package cn.gyw.corejava.concurrent.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * 封装非标准的取消操作
 * @param <T>
 */
public interface CancellableTask<T> extends Callable<T> {

    void cancel();
    RunnableFuture<T> newTask();
}
