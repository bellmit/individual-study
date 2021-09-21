package cn.gyw.glearn.algorithm.stack;

import java.util.Stack;

/**
 * 题目：
 * 编写一个类，用两个栈实现队列
 *
 * 要求：
 * 1、支持队列的基本操作（add、poll、peek）
 *
 * 实现方式：
 * 1.压入栈和弹出栈，职责单一
 * 2.如果压入栈要往弹出栈中压入数据，必须全部压入，才能让数据反序；
 * 3.在弹出栈数据存在的情况下，压入栈不能将数据压入弹出栈；
 */
public class QueueByTwoStack<T> {

    private Stack<T> stackPush;

    private Stack<T> stackPop;

    public QueueByTwoStack() {
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    /**
     * 入队
     *
     * @param element
     */
    public void add(T element) {
        this.stackPush.push(element);
    }

    /**
     * 出队：删除元素
     *
     * 区别于 remove ，空队列返回 null
     * @return
     */
    public T poll() {
        transfer();
        return stackPop.pop();
    }

    /**
     * 出队：不删除元素
     *
     * 区别于 element ，空队列返回 null
     * @return
     */
    public T peek() {
        transfer();
        return stackPop.peek();
    }

    public void printInnerInfo() {
        System.out.println("stackPush : " + stackPush);
        System.out.println("stackPop : " + stackPop);
    }

    private void transfer() {
        if (stackPop.isEmpty() && stackPush.isEmpty()) {
            throw new RuntimeException("Queue is empty!");
        } else if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
    }

}
