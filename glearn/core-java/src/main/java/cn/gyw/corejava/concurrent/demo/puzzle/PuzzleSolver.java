package cn.gyw.corejava.concurrent.demo.puzzle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 在解决器中找不到解答
 *
 * 结束条件
 * 1）时间限制，可以通过闭锁的getValue
 * 2）特定数量的次数
 *
 * @param <P>
 * @param <M>
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec) {
        super(puzzle, exec);
    }

    @Override
    protected Runnable newTask(P pos, M move, Node<P, M> node) {
        return super.newTask(pos, move, node);
    }

    class CountingSolverTask extends SolverTask {

        CountingSolverTask(P pos, M move, Node<P, M> node) {
            super(pos, move, node);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
