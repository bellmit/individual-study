package cn.gyw.glearn.algorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.glearn.algorithm.stack.StackWithMin;
import cn.gyw.glearn.algorithm.stack.StackWithMin2;

/**
 * 测试：设计一个有getMin功能的栈
 */
public class StackWithMinTest {

    private StackWithMin myStack1 = null;
    private StackWithMin2 myStack2 = null;

    @Before
    public void setUp() {
        myStack1 = new StackWithMin();
        myStack2 = new StackWithMin2();

        Integer[] integers = new Integer[] {3, 4, 5, 1, 2, 1};
        for (Integer integer : integers) {
            myStack1.push(integer);
            myStack2.push(integer);
        }
    }

    @Test
    public void shouldGetMinByMethod1() {
        Assert.assertEquals(Integer.valueOf(1), myStack1.getMin());
        myStack1.pop();
        Assert.assertEquals(Integer.valueOf(1), myStack1.getMin());
        myStack1.pop();
        Assert.assertEquals(Integer.valueOf(1), myStack1.getMin());
        myStack1.pop();
        Assert.assertEquals(Integer.valueOf(3), myStack1.getMin());
    }

    @Test
    public void shouldGetMinByMethod2() {
        Assert.assertEquals(Integer.valueOf(1), myStack2.getMin());
        myStack2.pop();
        Assert.assertEquals(Integer.valueOf(1), myStack2.getMin());
        myStack2.pop();
        myStack2.pop();
        Assert.assertEquals(Integer.valueOf(3), myStack2.getMin());
    }

    @Test
    public void shouldPrintInnerStackElements() {
        myStack1.printStack();
    }

}
