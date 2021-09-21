package cn.gyw.glearn.algorithm.link;

import java.util.Stack;

/**
 * 判断一个链表是否是回文结构
 *
 * 例如：
 * 1-2-1 ,true
 * 1-2-2-1 ,true
 *
 * 进阶：如果链表长度为N，时间复杂度达到O(N)，额外空间复杂度为O(1)
 */
public class Palindrome {

    /**
     * 利用栈结构判断是否是回文结构
     * @return
     */
    public boolean isPalindrome1(SingleNode head) {
        Stack<SingleNode> stack = new Stack<>();
        SingleNode cur = head;

        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }

        return true;
    }

    /**
     * 左右半区
     *
     * 利用栈结构
     * 时间复杂度：O(N)
     * 空间复杂度：O(N/2)
     * @param head
     * @return
     */
    public boolean isPalindrome2(SingleNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        SingleNode right = head.next;
        SingleNode cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        Stack<SingleNode> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 不需要栈和其他数据结构
     *
     * 空间复杂度：O(1)
     * 时间复杂度：O(N)
     *
     * 步骤：
     * 1、改变链表右半区，使右半区反转，最后指向中间节点，左边区开始节点为leftStart，同理右半区为rightStart
     * 2、leftStart、rightStart 同时向中间点移动，移动每一步同时比较左右节点，如果不相等，则不是回文结构
     * 3、不管返回true或者false，返回结果前都应该把链表还原
     * 4、链表还原后，返回检查结果
     * @param head
     * @return
     */
    public boolean isPalindrome3(SingleNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        SingleNode n1 = head;
        SingleNode n2 = head;
        while (n2.next != null && n2.next.next != null) { // 查找中间节点
            n1 = n1.next; // n1 -> 中部
            n2 = n2.next.next; // n2 -> 结尾
        }
        n2 = n1.next; // n2 -> 右半区第一个节点
        n1.next = null; // mid.next -> null
        SingleNode n3 = null;
        while (n2 != null) { // 右半区反转
            n3 = n2.next; // n3 -> 保存下一个节点
            n2.next = n1; // 下一个反转节点
            n1 = n2; // n1 移动
            n2 = n3; // n2 移动
        }
        n3 = n1; // n3 -> 保存最后一个节点
        n2 = head; // n2 ->左边第一个节点
        boolean res = true;
        while (n1 != null && n2 != null) { // 检查回文
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next; // 从左到中部
            n2 = n2.next; // 从右到中部
        }
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) { // 恢复链表
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }
}
