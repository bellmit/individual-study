package cn.gyw.corejava.concurrent.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 延迟任务
 * Created by guanyw on 2019/1/10.
 */
public class DelayTask implements Task, Delayed {

    private String val;
    private String taskName;
    /**
     * 到期时间
     */
    private final long time;

    private static final AtomicLong atomic = new AtomicLong(0);

    public DelayTask(long timeout, String taskName) {
        this.time = System.nanoTime() + timeout;
        this.taskName = taskName;
    }

    public void setValue(String val) {
        taskName = val;
    }

    @Override
    public void execute() {
        System.out.println("delay task , task name is : " + taskName);
    }

    /**
     * 返回与此对象相关的剩余延迟时间，以给定的时间单位表示
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    @Override
    public String toString() {
        return "DelayTask{" + taskName +"}";
    }
}
