package cn.gyw.glearn.algorithm.link;

/**
 * 环形链表问题
 */
public class RingLink {

    /**
     * 约瑟夫问题
     *
     * @param head 一个环形单向链表的头节点head
     * @param m    报数的值 m
     * @return 最后
     */
    public SingleNode josephusKill1(SingleNode head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        SingleNode last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    /**
     * 约瑟夫问题
     *
     * @param head 一个环形单向链表的头节点head
     * @param m    报数的值 m
     * @return
     */
    public SingleNode josephusKill2(SingleNode head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        SingleNode cur = head.next;
        int tmp = 1;
        while (cur != head) {
            tmp++;
            cur = cur.next;
        }
        tmp = getLive(tmp, m);
        while (--tmp != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    private int getLive(int i, int m) {
        if (i == 1) {
            return 1;
        }
        return (getLive(i - 1, m) + m - 1) % i + 1;
    }

}
