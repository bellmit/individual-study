package cn.gyw.glearn.algorithm.search;

/**
 * 普通搜索
 */
public class NormalSearch {

    /**
     * 查找最大值
     * <p>
     * 时间复杂度 O(n)
     *
     * @param arr
     * @return
     */
    public int findMax(int[] arr) {
        int len = arr.length;
        int max = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
