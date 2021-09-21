package cn.gyw.corejava.concurrent.demo.puzzle;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * 并发的谜题解答器
 *
 * 问题：
 * 若问题没有解答，getValue 将永远阻塞
 */
public class ConcurrentPuzzleSolver<P, M> {

    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;

    private final ConcurrentHashMap<P, Boolean> seen;

    protected final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec) {
        this.puzzle = puzzle;
        this.exec = exec;
        this.seen = new ConcurrentHashMap<>();
    }

    public List<M> solve() throws InterruptedException {
        try {
            P pos = puzzle.initialPosition();
            exec.execute(newTask(pos, null, null));

            // 阻塞直到回去结果
            Node<P, M> solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    protected Runnable newTask(P pos, M move, Node<P, M> node) {
        return new SolverTask(pos, move, node);
    }

    class SolverTask extends Node<P, M> implements Runnable {

        SolverTask(P pos, M move, Node<P, M> node) {
            super(pos, move, node);
        }

        @Override
        public void run() {
            if (solution.isSet()
                || seen.putIfAbsent(pos, true) != null) {
                return; // 已经解答或已遍历该位置
            }

            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    exec.execute(newTask(puzzle.move(pos, m), m, this));
                }
            }

        }
    }
}
