package cn.gyw.corejava.concurrent.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Fork Join demo 1
 * <p>
 * 批量发送消息
 */
public class BatchSendMsgTask extends RecursiveAction {

    private final int THRESHOLD = 10;

    private int start;
    private int end;
    private List<String> list;

    public BatchSendMsgTask(int start, int end, List<String> list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    @Override
    protected void compute() {
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + list.get(i));
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new BatchSendMsgTask(start, middle, list), new BatchSendMsgTask(middle, end, list));
        }
    }

}
