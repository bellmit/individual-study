package cn.gyw.corejava.concurrent.atomics;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 域更新器
 */
public class FieldUpdaterTest {

    /**
     * 引用更新器
     */
    @Test
    public void referenceUpdater() {
        AtomicReferenceFieldUpdater<Node, Node> updater =
                AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");
        Node node = new Node(0);
        Node node1 = new Node(1);
        node.next = node1;

        updater.compareAndSet(node, node, node);

        System.out.println(node);
    }

    private class Node {
        private int value;

        // Must be volatile type
        public volatile Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
