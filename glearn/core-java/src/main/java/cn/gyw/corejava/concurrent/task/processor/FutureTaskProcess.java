package cn.gyw.corejava.concurrent.task.processor;

import cn.gyw.corejava.concurrent.task.Task;

import java.util.concurrent.Callable;

/**
 * 有返回值的任务
 */
public class FutureTaskProcess implements Callable<Task> {

    private Task task;

    public FutureTaskProcess(Task task) {
        this.task = task;
    }

    @Override
    public Task call() throws Exception {
        // 异步处理逻辑
        task.execute();
        return task;
    }

}
