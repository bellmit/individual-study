package cn.gyw.corejava.concurrent.demo;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 只执行一次的服务
 * <p>
 * 使用私有的Executor ，并且该Executor 生命周期受限于方法调用
 */
public class OnceServiceInMethod {


    /**
     * 在多台主机上并行的检查新邮件，当所有邮件任务都执行完成后，
     * 关闭Executor 并等待结束
     *
     * @return
     */
    boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        final AtomicBoolean hasNewMail = new AtomicBoolean(false);

        try {
            for (final String host : hosts) {
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
            exec.awaitTermination(timeout, unit);
        }

        return hasNewMail.get();
    }

    // 检查邮件，若有新邮件，返回true
    private boolean checkMail(String host) {
        return true;
    }
}
