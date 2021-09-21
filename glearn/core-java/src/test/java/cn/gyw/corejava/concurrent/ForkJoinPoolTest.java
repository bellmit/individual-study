package cn.gyw.corejava.concurrent;

import cn.gyw.corejava.AbstractTest;
import cn.gyw.corejava.concurrent.forkjoin.BatchSendMsgTask;
import cn.gyw.corejava.concurrent.forkjoin.SumTask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolTest extends AbstractTest {

    @Test
    public void sumOfNums() {
        List<Integer> datas = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            datas.add(i);
        }

        SumTask task = new SumTask(0, datas.size(), datas);

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> result = pool.submit(task);

        if (task.isCompletedNormally()) {
            log.info("task processed success normally");
        }

        log.info("最终结果：{}", result.invoke());

        pool.shutdown();
    }

    @Test
    public void batchSendMsg() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            datas.add(String.valueOf(i + 1));
        }

        BatchSendMsgTask task = new BatchSendMsgTask(0, datas.size(), datas);

        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);

        do {
            printCurrentThreadInfo(pool);

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("error :{}", e);
            }
        } while (!task.isDone());

        if (task.isCompletedNormally()) {
            log.info("task processed success normally");
        }

        pool.shutdown();

    }

    private void printCurrentThreadInfo(ForkJoinPool forkJoinPool) {
        log.info("active thread count :{}", forkJoinPool.getActiveThreadCount());
    }

}
