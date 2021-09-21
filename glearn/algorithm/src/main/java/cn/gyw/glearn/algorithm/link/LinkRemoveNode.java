package cn.gyw.glearn.algorithm.link;

/**
 * 在单链表和双链表中删除倒数第K个节点
 * <p>
 * 题目：
 * 分别实现两个函数,一个可以删除单链表中倒数第K个节点，另一个可以删除双链表中倒数第K个节点
 * <p>
 * 要求：
 * 如果链表长度为N，时间复杂度达到O(N)，空间复杂度O(N)
 */
public class LinkRemoveNode {

    public SingleNode removeLastKthNode(SingleNode head, int lastKth) {
        if (head == null || lastKth < 1) {
            return head;
        }

        SingleNode cur = head;
        while (cur != null) {
            lastKth--;
            cur = cur.next;
        }
        if (lastKth == 0) {
            return head.next;
        }
        if (lastKth < 0) {
            cur = head;
            for (int i = 0; i < -lastKth - 1; i++) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }

        return head;
    }

    public DoubleNode removeLastKthNode(DoubleNode head, int lastKth) {
        if (head == null || lastKth < 1) {
            return head;
        }

        DoubleNode cur = head;
        while (cur != null) {
            lastKth--;
            cur = cur.next;
        }
        // 等于0，刚好头节点
        if (lastKth == 0) {
            head.next.last = null;
            return head.next;
        }
        // 小于0，就中间节点
        if (lastKth < 0) {
            cur = head;
            while (++lastKth != 0) {
                cur = cur.next;
            }

            DoubleNode newNode = cur.next.next;
            cur.next = newNode;
            if (newNode != null) {
                newNode.last = cur;
            }
        }
        // 大于0，不存在的第K个节点
        return head;
    }
}
