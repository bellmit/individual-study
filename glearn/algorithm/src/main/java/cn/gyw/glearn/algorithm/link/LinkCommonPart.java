package cn.gyw.glearn.algorithm.link;


/**
 * 打印两个有序链表的公共部分
 * <p>
 * 题目：
 * 给定两个有序链表的头指针head1 和 head2 ，打印两个链表的公共部分。
 */
public class LinkCommonPart {

    /**
     * 打印
     * @param head1
     * @param head2
     */
    public void printCommonPart(SingleNode head1, SingleNode head2) {
        System.out.print("Common part : ");
        while (head1 != null && head2 != null) {
            if (head1.value > head2.value) {
                head2 = head2.next;
            } else if (head1.value < head2.value) {
                head1 = head1.next;
            } else {
                System.out.print(head1.value + "\t");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
        System.out.println("打印完成");
    }
}
