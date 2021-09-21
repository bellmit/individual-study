package cn.gyw.glearn.algorithm.interview.pdd;

/**
 * 给定一个无序数组，包含正数、负数和0，要求从中找出3个数的乘积，使得乘积最大，
 *
 * 要求时间复杂度：O(n)，空间复杂度：O(1)
 * Created by guanyw on 2019/2/12.
 */
public class PddDemo01 {

    public static void main(String[] args) {
        PddDemo01 demo = new PddDemo01();
        int[] arr = new int[] {2,3,8,-5,6,2,0,-4,-2,11};

        // 方法
        System.out.println(demo.findMaxThree(arr));
    }

    private int findMaxThree(int[] arr) {
        int max1 = 0, max2 = 0, max3 = 0, min1 = 0, min2 = 0;
        for (int i = 0 ; i < arr.length; i ++) {
            if (arr[i] > max1) {
                max3 = max2;
                max2 = max1;
                max1 = arr[i];
            } else if (arr[i] > max2) {
                max3 = max2;
                max2 = arr[i];
            } else if (arr[i] > max3) {
                max3 = arr[i];
            } else if (arr[i] < min2) {
                min1 = min2;
                min2 = arr[i];
            } else if (arr[i] < min1) {
                min1 = arr[i];
            }
        }
        return (max1 * max2 * max3) > (max1 * min1 * min2) ? (max1 * max2 * max3) : (max1 * min1 * min2);
    }
}
