package cn.gyw.glearn.algorithm.stack;

import java.util.Objects;
import java.util.Stack;

/**
 * 一个栈实现另一个栈的排序
 *
 * 题目：一个栈中元素为整型，现在想将该栈从顶到底按从大到小顺序排序，只准申请一个栈。
 * 除此之外，可以申请新的变量，但不能申请额外的数据结构。如何完成排序。
 */
public class StackSortedByStack {

    private StackSortedByStack() {}

    /**
     *
     * @param stack
     */
    public static void sort(Stack<Integer> stack) {
        if (Objects.isNull(stack)) {
            throw new NullPointerException("Stack is null");
        }
        // 辅助栈
        Stack<Integer> help = new Stack<>();
        Integer cur = null;
        while (!stack.isEmpty()) {
            cur = stack.pop();
            while (!help.isEmpty() && help.peek() < cur) {
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }

    /*
    * 个人想法：用递归实现，没有完成
    * */
//    private static void newElement(Integer ele) {
//        if (data.isEmpty()) {
//            data.push(ele);
//            return;
//        }
//        Integer firstData = data.pop();
//        if (firstData < ele) {
//            newElement(ele);
//        } else {
//            data.push(firstData);
//            data.push(ele);
//            return;
//        }
//    }
}
