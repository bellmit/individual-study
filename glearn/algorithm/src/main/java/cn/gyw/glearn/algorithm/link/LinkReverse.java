package cn.gyw.glearn.algorithm.link;

/**
 * 倒转一个链表
 * 反转单向和双向链表
 * <p>
 * 要求：
 * 如果链表长度为N，时间复杂度要求为O(N)，额外空间复杂度要求为O(1)
 */
public class LinkReverse {

    /**
     * 反转单向链表
     *
     * @param head
     * @return
     */
    public SingleNode reverseList(SingleNode head) {
        SingleNode pre = null;
        SingleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 反转双向链表
     *
     * @param head
     * @return
     */
    public DoubleNode reverseList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

}
