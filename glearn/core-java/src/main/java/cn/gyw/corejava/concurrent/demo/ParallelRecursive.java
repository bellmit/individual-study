package cn.gyw.corejava.concurrent.demo;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 深度优先算法遍历一棵树
 *
 * 将串行递归转换成并行递归
 */
public class ParallelRecursive {

    public <T> void sequentialRecursive(List<TreeNode<T>> nodes,
                                        Collection<T> results) {
        for (TreeNode<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    public <T> void parallelRecursive(final Executor executor,
                                      List<TreeNode<T>> nodes,
                                      final Collection<T> results) {
        for (TreeNode<T> node : nodes) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(node.compute());
                }
            });

            parallelRecursive(executor, node.getChildren(), results);
        }
    }

    /**
     * 等待通过并行的方式计算结果
     * @param nodes
     * @param <T>
     * @return
     */
    public <T> Collection<T> getParallelResults(List<TreeNode<T>> nodes) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> currentQueue = new ConcurrentLinkedQueue<>();
        parallelRecursive(exec, nodes, currentQueue);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return currentQueue;
    }

    private static class TreeNode<T> {

        private List<TreeNode<T>> children;

        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public T compute() {
            return null;
        }

    }
}
