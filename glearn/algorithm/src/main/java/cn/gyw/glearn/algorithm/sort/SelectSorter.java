package cn.gyw.glearn.algorithm.sort;

/**
 * 选择排序算法
 *
 * 排序规则：从小到大
 * 遍历最小的数放在序列前，每一轮只和第一个数交换
 *
 * 时间复杂度：O(n^2)
 */
public class SelectSorter implements ISorter {

    private int runCnt;

    @Override
    public void sort(int[] data) {
        int len = data.length;
        int min;
        for (int i = 0 ; i < len - 1 ; i ++) {
            min = i;
            for (int j = i + 1 ; j < len ; j ++) {
                // 查询选择最小的元素
                if (data[j] < data[min]) {
                    min = j;
                }
                runCnt ++;
            }

            if (min == i) {
                continue; // 当前值时最小值，不交换，继续
            }
            // 交换元素
            int tmp = data[i];
            data[i] = data[min];
            data[min] = tmp;
        }
    }

    @Override
    public int getRunCnt() {
        return runCnt;
    }
}
