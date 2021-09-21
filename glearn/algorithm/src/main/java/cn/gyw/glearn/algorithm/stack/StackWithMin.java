package cn.gyw.glearn.algorithm.stack;

import java.util.Stack;

/**
 * 题目：
 * 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小的元素
 *
 * 要求：
 * 1、pop、push、getMin 操作的时间复杂度都是O(1)
 * 2、设计的栈类型可以使用现成的栈结构
 *
 * 实现方法一：
 * 压栈：判断，如果小于等于，入栈，如果大于，不操作；
 * 出栈：如果出栈的元素和小数栈栈顶元素相同，小数栈出栈，否则不出栈；
 *
 * 特点：压栈，节省空间，出栈需要花费时间，时间换空间；
 */
public class StackWithMin {

    private Stack<Integer> stackData;

    private Stack<Integer> stackMin;

    public StackWithMin() {
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    /**
     * 弹出规则
     *
     * @return
     */
    public Integer pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("null element");
        }
        Integer currentEle = this.stackData.pop();
        if (currentEle == this.stackMin.peek()) {
            this.stackMin.pop();
        }
        return currentEle;
    }

    /**
     * 压入规则
     *
     * @param element
     */
    public void push(Integer element) {
        this.stackData.push(element);

        if (stackMin.isEmpty()) {
            stackMin.push(element);
        } else {
            Integer tmp = stackMin.peek();
            if (tmp >= element) {
                stackMin.push(element);
            }
        }
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public Integer getMin() {
        if (stackMin.isEmpty()) {
            throw new RuntimeException("null element");
        }
        return stackMin.peek();
    }

    public void printStack() {
        System.out.println("stackData : " + stackData);
        System.out.println("stackMin : " + stackMin);
    }

}