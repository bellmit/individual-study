package cn.gyw.glearn.algorithm;

import org.junit.Before;
import org.junit.Test;

import cn.gyw.glearn.algorithm.stack.StackSortedByStack;

import java.util.Stack;

/**
 * 一个栈实现另一个栈的排序
 */
public class StackSortedByStackTest {

    private Stack<Integer> datas;

    @Before
    public void before() {
        datas = new Stack<>();
        datas.push(5);
        datas.push(4);
        datas.push(3);
        datas.push(2);
        datas.push(1);
    }

    @Test
    public void shouldSorted() {
        System.out.println("未排序：" + datas);
        StackSortedByStack.sort(datas);
        System.out.println("排序后：" + datas);
    }
}
