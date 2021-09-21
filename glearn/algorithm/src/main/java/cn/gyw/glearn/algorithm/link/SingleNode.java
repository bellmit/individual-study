package cn.gyw.glearn.algorithm.link;

public class SingleNode {

    public int value;
    public SingleNode next;

    public SingleNode(int data) {
        this.value = data;
    }

    public SingleNode add(SingleNode next) {
        this.next = next;
        return next;
    }
}
