package cn.gyw.glearn.algorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.glearn.algorithm.stack.QueueByTwoStack;

/**
 * Created by guanyw on 2019/3/5.
 */
public class QueueByTwoStackTest {

    private QueueByTwoStack queue;

    @Before
    public void setUp() {
        queue = new QueueByTwoStack();
        Integer[] integers = new Integer[] {3, 4, 5, 1, 2, 1};
        for (Integer integer : integers) {
            queue.add(integer);
        }
    }

    /**
     * 队列基本功能 FIFO（先进先出）
     */
    @Test
    public void shouldFIFO() {
        Assert.assertEquals(Integer.valueOf(3), queue.poll());
        Assert.assertEquals(Integer.valueOf(4), queue.poll());
        queue.printInnerInfo();
        queue.add(9);
        Assert.assertEquals(Integer.valueOf(5), queue.poll());
        Assert.assertEquals(Integer.valueOf(1), queue.poll());
        queue.printInnerInfo();
        Assert.assertEquals(Integer.valueOf(2), queue.poll());
        Assert.assertEquals(Integer.valueOf(1), queue.poll());
        Assert.assertEquals(Integer.valueOf(9), queue.poll());
    }

}
