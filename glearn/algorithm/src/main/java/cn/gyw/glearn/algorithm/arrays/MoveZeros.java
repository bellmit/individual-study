package cn.gyw.glearn.algorithm.arrays;

/**
 * 给定一个数组nums，写一个函数，将数组中所有的0挪到数组的末尾，
 * 而维持其他元素的相对位置
 * <p>
 * e.g.
 * [0,1,0,3,12] -> [1,3,12,0,0]
 * 
 * @see MoveZerosTest
 */
public class MoveZeros {

    /**
     * 1. 取出所有非0元素，放到新数组中
     * 时间复杂度 O(n)
     * 空间父再度 O(n)
     */
    public void solution1(int[] nums) {
        int[] nonZeroArray = new int[nums.length];
        int idx = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] > 0) {
                nonZeroArray[idx++] = nums[i];
            }
        }
        for (int i = 0, len = nonZeroArray.length; i < len; i++) {
            nums[i] = nonZeroArray[i];
        }
        for (int j = idx, len = nums.length; j < len; j++) {
            nums[j] = 0;
        }
    }

    /**
     * 2. [0, k) 存储非0元素，其他位置为0
     *
     * @param nums
     * @return
     */
    public void solution2(int[] nums) {
        int k = 0; // [0, k) 存储非0元素
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] > 0) {
                nums[k] = nums[i];
                k++;
            }
        }
        for (int len = nums.length; k < len; k++) {
            nums[k] = 0;
        }
    }

    /**
     * 3. 交换元素的位置
     *
     * @param nums
     * @return
     */
    public void solution3(int[] nums) {
        int k = 0; // [0, k) 存储非0 元素
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] > 0) {
                if (i != k) {
                    int tmp = nums[k];
                    nums[k] = nums[i];
                    nums[i] = tmp;
                    k++;
                } else {
                    k++;
                }
            }
        }
    }
}
