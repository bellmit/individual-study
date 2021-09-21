package cn.gyw.corejava.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/*
Fork/Join 框架
 */
public class ForkJoinTaskTest extends RecursiveTask<Integer> {

    public static final int threshold = 2;

    private int start;
    private int end;

    public ForkJoinTaskTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        // 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (int i = start ; i <= end ; i ++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分成两个子任务计算
            int middle = (start + end) / 2;
            ForkJoinTaskTest left = new ForkJoinTaskTest(start, middle);
            ForkJoinTaskTest right = new ForkJoinTaskTest(middle + 1, end);

            // 执行子任务
            left.fork();
            right.fork();

            // 等待任务执行结束合并其结果
            int leftResult = left.join();
            int rightResult = right.join();

            sum = leftResult + rightResult;

        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 生成一个计算任务，计算1+2+3+4
        ForkJoinTaskTest task = new ForkJoinTaskTest(1, 100);

        // 执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
