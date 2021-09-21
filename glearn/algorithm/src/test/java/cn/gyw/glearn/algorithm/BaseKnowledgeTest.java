package cn.gyw.glearn.algorithm;

import cn.gyw.glearn.algorithm.search.BinarySearch;
import cn.gyw.glearn.algorithm.search.NormalSearch;
import cn.gyw.glearn.algorithm.sort.ISorter;
import cn.gyw.glearn.algorithm.sort.MergeSorter;
import cn.gyw.glearn.algorithm.sort.SelectSorter;
import cn.gyw.glearn.algorithm.util.DataGenerator;
import org.junit.Test;

import cn.gyw.glearn.algorithm.base.AbstractTest;

/**
 * 基本知识测试
 * <p>
 * 1、基本时间复杂度和数据规模的关系测试
 * 2、
 *
 * @author yuewu_guan
 */
public class BaseKnowledgeTest extends AbstractTest {

    /***************************************************/
    /* 时间复杂度 */

    /**
     * O(n)时间复杂度
     */
    @Test
    public void sumWithTimeO_n() {
        for (int i = 10; i < 26; i++) {
            int n = (int) Math.pow(2, i);
            startTime();
            new NormalSearch().findMax(DataGenerator.generateRandomArray(n, 0, 100_000_000));
            System.out.print("数据量：2^" + i + "=" + n);
            endTime();
        }
    }

    /**
     * O(n^2)时间复杂度
     */
    @Test
    public void sortWithTimeO_n2() {
        ISorter sorter = new SelectSorter();
        for (int i = 10; i < 15; i++) {
            int n = (int) Math.pow(2, i);
            startTime();
            sorter.sort(DataGenerator.generateRandomArray(n, 0, 100_000_000));
            System.out.print("数据量：2^" + i + "=" + n);
            endTime();
        }
    }

    /**
     * O(logn)时间复杂度
     */
    @Test
    public void searchWithO_logn() {
        for (int i = 10; i < 28; i++) {
            int n = (int) Math.pow(2, i);
            startTime();
            new BinarySearch().search(DataGenerator.generateOrderedArray(n), 0);
            System.out.print("数据量：2^" + i + "=" + n);
            endTime();
        }
    }

    /**
     * O(nlogn)时间复杂度
     */
    @Test
    public void sortWithO_nlogn() {
        for (int i = 10; i < 26; i++) {
            int n = (int) Math.pow(2, i);
            startTime();
            new MergeSorter().sort(DataGenerator.generateRandomArray(n, 1, 100_000_000));
            System.out.print("数据量：2^" + i + "=" + n);
            endTime();
        }
    }

    /****************************************************/
    /* 空间复杂度 */
    @Test
    public void sumWithSpaceO_1() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
    }

    @Test
    public void sumWithSpaceO_n() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
    }

}
