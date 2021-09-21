package cn.gyw.glearn.algorithm.link;

/**
 * 双向链表节点
 */
public class DoubleNode {

    public int value;
    public DoubleNode last;
    public DoubleNode next;

    public DoubleNode(int data) {
        this.value = data;
    }

    public DoubleNode add(DoubleNode next) {
        next.last = this;
        this.next = next;
        return next;
    }
}
