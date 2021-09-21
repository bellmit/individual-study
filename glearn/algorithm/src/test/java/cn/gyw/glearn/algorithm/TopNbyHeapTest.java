package cn.gyw.glearn.algorithm;

import org.junit.Test;

/**
 * TopN 查询最大的N 个数
 */
public class TopNbyHeapTest {

    /**
     * 查询最大的10个数
     */
    @Test
    public void findTop10() {
        int[] data = new int[]{0, 1, 3, 4, 5, 11, 23, 45, 66, 77, 8, 9, 19, 29, 49, 28};
        TopNbyHeap topNbyHeap = new TopNbyHeap();
        int[] result = topNbyHeap.findTopN(10, data);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + "\t");
        }
    }

}
