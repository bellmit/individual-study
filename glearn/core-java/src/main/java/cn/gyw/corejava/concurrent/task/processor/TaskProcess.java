package cn.gyw.corejava.concurrent.task.processor;

import cn.gyw.corejava.concurrent.task.Task;

/**
 * 自定义的任务线程
 *
 * Created by guanyw on 2018/12/19.
 */
public class TaskProcess implements Runnable {

    private Task task;

    public TaskProcess(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.execute();
    }
}
