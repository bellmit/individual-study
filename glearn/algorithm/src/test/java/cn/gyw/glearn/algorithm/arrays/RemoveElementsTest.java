package cn.gyw.glearn.algorithm.arrays;

import org.junit.Assert;
import org.junit.Test;

import cn.gyw.glearn.algorithm.arrays.RemoveElements;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RemoveElementsTest {

    @Test
    public void solution1() {
        int[] data = new int[] {0,1,0,3,12};
        System.out.println(">>" + Arrays.toString(data));

        int result = new RemoveElements().solution1(data, 0);
        Assert.assertEquals(3, result);

        result = new RemoveElements().solution1(data, 1);
        Assert.assertEquals(4, result);
    }
}