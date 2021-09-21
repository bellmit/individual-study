package cn.gyw.glearn.algorithm.datastructure.stack;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 并发栈
 * <p>
 * 通过Treiber 算法构造的非阻塞栈
 */
public class ConcurrentStack<E> {

    private AtomicReference<Node<E>> top = new AtomicReference<>();

    /**
     * 元素入栈
     *
     * @param item
     */
    public void push(E item) {
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do { // 先执行后检查
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> curHead;
        do {
            curHead = top.get();
            if (Objects.isNull(curHead)) {
                return null;
            }
        } while (!top.compareAndSet(curHead, curHead.next));
        return curHead.item;
    }

    // 栈中元素
    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E item) {
            this.item = item;
        }
    }
}
