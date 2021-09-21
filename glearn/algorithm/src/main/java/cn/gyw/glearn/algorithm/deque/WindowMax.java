package cn.gyw.glearn.algorithm.deque;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 生成窗口最大值数组
 *
 * 题目：
 * 有一个整数数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次滑动一个位置。
 * 例如数组：[4,3,5,4,3,3,6,7]，窗口大小为3时：
 * 生成[5,5,5,4,6,7]
 *
 * 如果数组长度为n，窗口大小为w，则一共产生n-w+1个窗口的最大值。
 * 实现一个函数：
 * 1）输入：整数数组arr，窗口大小为w；
 * 2）输出：一个长度为n-w+1的数组res，res[i] = 表示每一种窗口状态下的最大值
 */
public class WindowMax {


    public int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 0) {
            throw new NullPointerException("Arguments is null");
        }
        int[] res = new int[arr.length - w + 1];

        Deque<Integer> maxDeque = new LinkedList<>();
        for (int i = 0 ; i < arr.length ; i ++) {
            // 如果队列为空，序号直接入栈
            if (maxDeque.isEmpty()) {
                maxDeque.push(i);
            }
            // 如果不为空且当前值小于队列最后一个序列的值，则直接序号入队
            while (!maxDeque.isEmpty() && arr[i] >= arr[maxDeque.peekLast()]) {
                maxDeque.pollLast();
            }
            // 剩下的就是当前值小于队列最后一个序列的值，直接入队
            maxDeque.addLast(i); // 不能用push，这个方法类似addFirst，在队头添加
            // 出队规则：如果对头的序号等于i-w,即 0 = 3 - 3 ，到i=3时，序号0就过期了
            if (maxDeque.peekFirst() == i - w) {
                maxDeque.pollFirst();
            }
            // 获取最大值数组，从下标 w - 1 开始
            if (i >= (w - 1)) {
                res[i - w + 1] = arr[maxDeque.peekFirst()];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] sourceData = new int[] {4,3,5,4,3,3,6,7};

        WindowMax windowMax = new WindowMax();
        int[] result = windowMax.getMaxWindow(sourceData, 3);
        for (int i : result) {
            System.out.print(i + "\t");
        }
    }
}
