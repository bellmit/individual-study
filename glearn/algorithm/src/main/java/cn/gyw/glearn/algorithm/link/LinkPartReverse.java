package cn.gyw.glearn.algorithm.link;

/**
 * 反转部分单向链表
 *
 * 【题目】
 * 给定一个单向链表的头节点head，以及两个整数from和to，在单向链表上把from个节点到第to个节点这一部分进行反转。
 * 例如：
 * 1->2->3->4->5->null, from=2,to=4
 * 调整结果为：1->4->3->2->5->null
 * 1->2->3->null, from=1,to=3
 * 调整结果为：3->2->1->null
 *
 * 【要求】
 * 1、如果链表长度为N，时间复杂度要求为O(N),额外空间复杂度为O(1)
 * 2、如果不满足1<=from<=to<=N，则不用调整
 */
public class LinkPartReverse {

    public SingleNode reversePart(SingleNode head, int from, int to) {
        if (from > to) {
            return head;
        }
        int len = 0;
        SingleNode cur = head;
        // from - 1的节点
        SingleNode fPre = null;
        // to + 1 的节点
        SingleNode tPos = null;

        while(cur != null) {
            len ++;
            fPre = from -1 == len ? cur : fPre;
            tPos = to + 1 == len ? cur : tPos;
            cur = cur.next;
        }
        if (from > to || from < 1 || to > len) {
            return head;
        }
        cur = fPre == null ? head : fPre.next;

        SingleNode node2 = cur.next;
        cur.next = tPos;
        SingleNode next = null;
        while (node2 != tPos) {
            next = node2.next;
            node2.next = cur;
            cur = node2;
            node2 = next;
        }
        if (fPre != null) {
            fPre.next = cur;
            return head;
        }
        return cur;
    }

}
