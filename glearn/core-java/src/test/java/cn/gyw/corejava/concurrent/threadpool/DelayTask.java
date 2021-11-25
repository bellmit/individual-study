package cn.gyw.corejava.concurrent.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 延迟任务
 */
public class DelayTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DelayTask.class);

    private Integer taskId;
    private String key;
    private boolean printTime;

    public DelayTask(Integer taskId, String key, boolean printTime) {
        this.taskId = taskId;
        this.key = key;
        this.printTime = printTime;
    }

    @Override
    public void run() {
        if (printTime) {
            log.info("===delayTask [{}] start...", taskId);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("===delayTask [{}] end.", taskId);
        } else {
            log.info("===delayTask [{}] noPrintTime start...", taskId);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("===delayTask [{}] noPrintTime end.", taskId);
        }
    }
}
