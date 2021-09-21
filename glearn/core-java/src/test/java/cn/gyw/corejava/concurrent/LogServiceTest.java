package cn.gyw.corejava.concurrent;

import cn.gyw.corejava.concurrent.demo.LogServiceByExecutorService;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 自定义的日志服务测试
 *
 * @see cn.gyw.corejava.concurrent.demo.LogService
 * @see cn.gyw.corejava.concurrent.demo.LogServiceByExecutorService
 */
public class LogServiceTest {


    @Test
    public void executorService() throws InterruptedException {
        LogServiceByExecutorService logger = new LogServiceByExecutorService();

        ExecutorServiceInfoService infoService = new ExecutorServiceInfoService(logger.getTaskExec());
        infoService.start();

        for (int i = 0 ; i < 10 ; i ++) {
            logger.log("log >>" + i);
        }

        // 任务在队列中，停止服务，测试日志是否会丢失
        logger.stop();

        TimeUnit.MINUTES.sleep(3);
    }

}
