package cn.gyw.glearn.algorithm;

import org.junit.Test;

import cn.gyw.glearn.algorithm.base.AbstractTest;
import cn.gyw.glearn.algorithm.sort.BubbleSorter;
import cn.gyw.glearn.algorithm.sort.ISorter;
import cn.gyw.glearn.algorithm.sort.QuickSorter;
import cn.gyw.glearn.algorithm.sort.SelectSorter;
import cn.gyw.glearn.algorithm.util.DataGenerator;

/**
 * 排序算法测试
 */
public class SorterTest extends AbstractTest {
    // mock 数据
    private int[] data;
    // 排序器
    private ISorter sorter;

    /**
     * 快速排序
     */
    @Test
    public void quickSort() {
        sorter = new QuickSorter();
        sorter.sort(data);
    }

    /**
     * 选择排序 O(n^2)
     */
    @Test
    public void select() {
        sorter = new SelectSorter();
        for (int i = 10 ; i < 15 ;i ++) {
        	int n = (int) Math.pow(2, i);
        	startTime();
        	sorter.sort(DataGenerator.generateRandomArray(n, 1, 100_000_000));
        	endTime();
        }
    }

    /**
     * 冒泡排序
     */
    @Test
    public void bubble() {
        sorter = new BubbleSorter();
        sorter.sort(data);
    }
}
