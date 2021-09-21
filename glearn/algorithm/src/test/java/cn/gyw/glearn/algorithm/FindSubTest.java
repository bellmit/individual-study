package cn.gyw.glearn.algorithm;

import org.junit.Test;

/**
 * 从元素中寻找符合要求的子串
 */
public class FindSubTest {

    /**
     * 从一个整型数组中，获取最长的递增子序列
     * [1,2,3,-1,-2,4,3,9,19,87,72]
     * 返回值：[3,9,19,87]
     */
    @Test
    public void findMaxAscOrder() {
        int[] source = {1,2,3,7,-1,-2,4,-2,9,19,87,72};
        // 递增子串的开始和结束
        int fromIndex = 0;
        int toIndex = 0;

        int num = 0;
        // 最大的递增序列元素数
        int maxNum = 0;
        int[] maxNumIndex = new int[2];

        for (int i = 0 ; i < source.length - 1 ; i++) {
            if (source[i] < source[i + 1]) {
                toIndex = i + 1;
                num = toIndex - fromIndex + 1;
            } else {
                if (num > maxNum) {
                    maxNum = num;
                    maxNumIndex[0] = fromIndex;
                    maxNumIndex[1] = toIndex;
                }
                fromIndex = toIndex + 1;
                num = 0;
            }
        }

        System.out.println("from : " + maxNumIndex[0] + " -> to :" + maxNumIndex[1]);
        System.out.println("from : " + source[maxNumIndex[0]] + " -> to :" + source[maxNumIndex[1]]);
    }
}
