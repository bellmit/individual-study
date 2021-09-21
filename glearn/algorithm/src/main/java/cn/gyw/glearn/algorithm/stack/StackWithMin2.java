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
 * 实现方式二：
 * 压栈：元素入栈，如果元素大于栈顶元素，小数栈栈顶重复入栈；
 * 出栈：元素正常出栈即可；
 *
 * 特点：压栈需要更多的空间，但是出栈花费的时间更少，空间换时间；
 */
public class StackWithMin2 {

    // 数据存储栈
    private Stack<Integer> stackData;

    // 最小数据栈
    private Stack<Integer> stackMin;

    public StackWithMin2() {
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    /**
     * 弹出规则
     *
     * @return
     */
    public Integer pop() {
        this.stackMin.pop();
        return this.stackData.pop();
    }

    /**
     * 压入规则
     *
     * 如果大于栈顶元素，栈顶元素重复入栈；
     * @param element
     */
    public void push(Integer element) {
        this.stackData.push(element);

        if (this.stackMin.isEmpty()) {
            this.stackMin.push(element);
        } else {
            Integer currentEle = stackMin.peek() < element ? stackMin.peek() : element;
            stackMin.push(currentEle);
        }
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public Integer getMin() {
        return this.stackMin.peek();
    }

}
