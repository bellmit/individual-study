package cn.gyw.glearn.algorithm.deque;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 最大值减去最小值小于或等于num的子数组数量
 *
 * 【题目】
 * 给定数组arr和整数num，共返回多少个子数组满足如下情况：
 * max(arr[i..j])-min(arr[i..j])<=num
 * max(arr[i..j]) 表示子数组 arr[i..j] 的最大值
 * min(arr[i..j]) 表示子数组 arr[i..j] 的最小值
 *
 * 【要求】
 * 如果数组长度为N，实现时间复杂度为O(N)的解法
 */
public class MaxMinSubEqNum {

    public int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        Deque<Integer> qmin = new LinkedList<>();
        Deque<Integer> qmax = new LinkedList<>();
        int i = 0 ;
        int j = 0;
        int res = 0;
        while (i < arr.length) {
            while (j < arr.length) {
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
                    qmin.pollLast();
                }
                qmin.addLast(j);
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
                    qmax.pollLast();
                }
                qmax.addLast(j);
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                j ++;
            }

            if (qmin.peekFirst() == i) {
                qmin.pollFirst();
            }

            if (qmax.peekFirst() == i) {
                qmax.pollFirst();
            }
            res += j - i;
            i++;
        }
        return res;
    }

    /**
     * 主函数测试
     * @param args
     */
    public static void main(String[] args) {

    }
}
