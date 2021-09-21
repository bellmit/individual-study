package cn.gyw.corejava.concurrent.locks;

import cn.gyw.corejava.model.BoundedBuffer;
import cn.gyw.corejava.concurrent.thread.pool.CustomThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * 条件变量学习
 *
 * Condition 与 ReentrantLock组合，更好的灵活性
 *
 * Created by guanyw on 2019/1/25.
 */
public class ConditionDemo {

    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer(2);
        Runnable putTask = ()-> {
            System.out.println(Thread.currentThread().getName() + ", 写");
            boundedBuffer.put("abv");
            System.out.println(Thread.currentThread().getName() + ", 写 Success");
        };
        Runnable takeTask = ()-> {
            System.out.println(Thread.currentThread().getName() + ", 读");
            System.out.println(Thread.currentThread().getName() + ":" + boundedBuffer.take());
        };

        ExecutorService executorService = CustomThreadPoolFactory.newCustomThreadPool();
        executorService.submit(takeTask);
        executorService.submit(putTask);
        executorService.submit(putTask);
        executorService.submit(putTask);

        executorService.shutdown();
    }

}
