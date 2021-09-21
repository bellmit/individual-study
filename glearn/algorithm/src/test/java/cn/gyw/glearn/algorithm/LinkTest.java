package cn.gyw.glearn.algorithm;

import cn.gyw.glearn.algorithm.link.*;
import org.junit.Test;

/**
 * 链表相关算法测试
 */
public class LinkTest {

    /**
     * 判断一个链表是否是回文结构
     */
    @Test
    public void testPalindrome() {
        SingleNode head = new SingleNode(10);
        head.add(new SingleNode(12))
                .add(new SingleNode(18))
                .add(new SingleNode(19))
                .add(new SingleNode(12))
                .add(new SingleNode(10));

        Palindrome palindrome = new Palindrome();
        boolean result = false;
        // 方法一
//        result = palindrome.isPalindrome1(head);
        // 方法二
//        result = palindrome.isPalindrome2(head);
        // 方法三
        result = palindrome.isPalindrome3(head);


        System.out.println(">>" + result);
    }

    /**
     * 反转单向和双向链表
     */
    @Test
    public void reverseList() {
        SingleNode head = new SingleNode(10);
        head.add(new SingleNode(12))
                .add(new SingleNode(13))
                .add(new SingleNode(16));

        SingleNode result = null;
        LinkReverse linkReverse = new LinkReverse();
        result = linkReverse.reverseList(head);

        System.out.println(result);

        DoubleNode head2 = new DoubleNode(10);
        head2.add(new DoubleNode(12))
                .add(new DoubleNode(13))
                .add(new DoubleNode(16));
        DoubleNode result2 = linkReverse.reverseList(head2);
        System.out.println(result2);
    }

    /**
     * 删除单链表的中间节点和a/b处的节点
     */
    @Test
    public void removeMidNode() {
        SingleNode head = new SingleNode(10);
        head.add(new SingleNode(12))
                .add(new SingleNode(13))
                .add(new SingleNode(16));

        LinkRemoveMidNode linkRemoveMidNode = new LinkRemoveMidNode();
        SingleNode result = null;
//      result = linkRemoveMidNode.removeMidNode(head);

        result = linkRemoveMidNode.removeByRatio(head, 1, 2);

        System.out.println();
    }

    /**
     * 删除双链表中倒数第K个节点
     */
    @Test
    public void doubleLinkRemoveLastKthNode() {
        DoubleNode head = new DoubleNode(10);
        head.add(new DoubleNode(12))
                .add(new DoubleNode(13))
                .add(new DoubleNode(16));

        DoubleNode result = new LinkRemoveNode().removeLastKthNode(head, 2);
        System.out.println(result);
    }

    /**
     * 删除单链表中倒数第K个节点
     */
    @Test
    public void singleLinkRemoveLastKthNode() {
        SingleNode head1 = new SingleNode(10);
        head1.add(new SingleNode(12))
                .add(new SingleNode(13))
                .add(new SingleNode(16));

        SingleNode result = new LinkRemoveNode().removeLastKthNode(head1, 2);
        System.out.println(result);
    }

    /**
     * 打印两个有序链表的公共部分
     */
    @Test
    public void linkCommonPartTest() {
        SingleNode head1 = new SingleNode(10);
        head1.add(new SingleNode(12))
                .add(new SingleNode(13))
                .add(new SingleNode(16));

        SingleNode head2 = new SingleNode(9);
        head2.add(new SingleNode(12))
                .add(new SingleNode(13))
                .add(new SingleNode(14));

        new LinkCommonPart().printCommonPart(head1, head2);
    }

}
