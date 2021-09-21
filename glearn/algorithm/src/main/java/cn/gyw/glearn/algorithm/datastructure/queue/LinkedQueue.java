package cn.gyw.glearn.algorithm.datastructure.queue;

import cn.gyw.platform.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞算法的链表
 * @param <E>
 */
@ThreadSafe
public class LinkedQueue<E> {

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);
    private final AtomicInteger counter = new AtomicInteger(0);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    // 中间状态，推进尾节点
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // 稳定状态，尝试插入新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // 插入成功，尝试推进尾节点
                        if (tail.compareAndSet(curTail, newNode)) {
                            // 计数
                            counter.incrementAndGet();
                            return true;
                        }
                    }
                }
            }
        }
    }

    public int size() {
        return counter.get();
    }

    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }
}
