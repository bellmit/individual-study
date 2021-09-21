package cn.gyw.glearn.algorithm.sort;

import java.util.Comparator;

/**
 * 冒泡排序算法
 *
 * 排序方式：从小到大排序
 * 相邻两个元素比较，如果前者大于后者，相邻两数交换，一次遍历，将最大的元素移至后面
 *
 * 时间复杂度：
 * O(n^2)
 */
public class BubbleSorter implements ISorter {

    // 执行次数
    private int runCnt = 0;

    @Override
    public void sort(int[] data) {
        int len = data.length;
        boolean needSweap = true;
        for (int i = 1 ; i < len && needSweap ; i ++) {
            needSweap = false;
            for (int j = 0; j < len - i ; j ++) {
                if (data[j] > data[j + 1]) {
                    int tmp = data[j];
                    data[j] = data[j+1];
                    data[j + 1] = tmp;
                    needSweap = true;
                }
                runCnt ++;
            }
        }
    }

    public <T extends Comparable<T>> void sort(T[] list) {
        boolean needSwap = true;
        for (int i = 1 , len = list.length ; i < len && needSwap ; i++) {
            needSwap = false;
            for (int j = 0 ; j < len - i ; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                    needSwap = true;
                }
            }
        }
    }

    public <T> void sort(T[] list, Comparator<T> comparator) {
        boolean needSwap = true;
        for (int i = 1 , len = list.length ; i < len && needSwap ; i++) {
            needSwap = false;
            for (int j = 0 ; j < len - i ; j++) {
                if (comparator.compare(list[j], list[j + 1]) > 0) {
                    T tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                    needSwap = true;
                }
            }
        }
    }

    @Override
    public int getRunCnt() {
        return runCnt;
    }
}
