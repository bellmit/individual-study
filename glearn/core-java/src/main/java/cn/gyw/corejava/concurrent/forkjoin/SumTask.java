package cn.gyw.corejava.concurrent.forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 20;

    private int start;
    private int end;
    private List<Integer> datas;

    public SumTask(int start, int end, List<Integer> datas) {
        this.start = start;
        this.end = end;
        this.datas = datas;
    }

    @Override
    protected Integer compute() {
        if ((end - start) <= THRESHOLD) {
            return subTotal();
        } else {
            int middle = (start + end) / 2;
            SumTask task1 = new SumTask(start, middle, datas);
            SumTask task2 = new SumTask(middle, end, datas);

            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        }
    }

    private Integer subTotal() {
        int sum = 0;
        for (int i = start; i < end;i++) {
            sum += datas.get(i);
        }
        System.out.println(Thread.currentThread().getName() + ": ∑(" + start + "~" + end + ")=" + sum);
        return sum;
    }
}
