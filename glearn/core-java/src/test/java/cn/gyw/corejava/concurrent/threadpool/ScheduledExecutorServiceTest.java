package cn.gyw.corejava.concurrent.threadpool;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务线程池
 *
 * @author yuewu.guan
 * @date 2021/11/25 13:09
 */
public class ScheduledExecutorServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ScheduledExecutorServiceTest.class);

    private static final ScheduledExecutorService DELAY_TASK_EXECUTOR = Executors.newScheduledThreadPool(2);

    /**
     * 一次性任务
     */
    @Test
    public void testOnceTask() {
        log.info("创建任务...");
        DELAY_TASK_EXECUTOR.schedule(new DelayTask(1, "testKey", true), 2, TimeUnit.SECONDS);
    }

    /**
     * 同一时刻新增1000个任务，如何执行
     */
    @Test
    public void testRun() {
        for (int i = 0; i < 1000; i++) {
            log.info("创建任务，任务ID=[{}]", i);
            DELAY_TASK_EXECUTOR.schedule(new DelayTask(i, "testKey", true), 2, TimeUnit.SECONDS);
        }
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterAll
    public static void tearDown() {
        try {
            DELAY_TASK_EXECUTOR.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
