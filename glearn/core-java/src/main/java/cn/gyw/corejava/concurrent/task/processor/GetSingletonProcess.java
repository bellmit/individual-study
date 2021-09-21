package cn.gyw.corejava.concurrent.task.processor;


import cn.gyw.corejava.model.SingletonObject;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程获取单例对象
 */
public class GetSingletonProcess implements Callable<SingletonObject> {

    private CountDownLatch countDownLatch;

    public GetSingletonProcess(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public SingletonObject call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 等待.....");
        countDownLatch.await();
        return SingletonObject.getInstance();
    }

}
