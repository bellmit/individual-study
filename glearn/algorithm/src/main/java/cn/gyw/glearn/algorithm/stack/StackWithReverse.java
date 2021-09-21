package cn.gyw.glearn.algorithm.stack;

import java.util.Stack;

/**
 * 如何仅用递归函数和和栈操作逆序一个栈
 *
 * 题目：一个栈依次压入1，2，3，4，5，那么从栈顶到栈底分别为5，4，3，2，1。
 * 将这个栈转置后，从栈顶到栈底为1，2，3，4，5，也就是实现栈中元素的逆序，
 * 但是只能用递归函数来实现，不能用其他数据结构。
 *
 * 分析：
 * 递归：1）递归条件；2）收敛条件
 * 设计2个递归函数
 */
public class StackWithReverse {

    /**
     * 递归函数二：逆序一个栈
     * @param dataStack
     * @param <T>
     */
    public static <T> void reverse(Stack<T> dataStack) {
        if (dataStack.isEmpty()) {
            return;
        }
        T data = getAndRemoveLastElement(dataStack);
        reverse(dataStack);
        dataStack.push(data);
    }

    /**
     * 递归函数一:将栈stack的栈底元素返回并移除
     * @param dataStack
     */
    public static <T> T getAndRemoveLastElement(Stack<T> dataStack) {
        T result = dataStack.pop();
        if (dataStack.isEmpty()) {
            return result;
        } else {
            T last = getAndRemoveLastElement(dataStack);
            dataStack.push(result);
            return last;
        }
    }

}
