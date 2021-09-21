package cn.gyw.glearn.algorithm.arrays;

/**
 * 给定一个数组nums和一个数值val，将数组中所有的val 的元素删除，并返回剩余的元素个数
 *
 * 思考：
 * 1. 如何定义删除？从数组中删除？还是放在数组的末尾？
 * 2. 剩余的元素是否需要保证原有的相对顺序？
 * 3. 是否由空间复杂度的要求？O(1)
 * 
 * @see RemoveElementsTest
 */
public class RemoveElements {

    /**
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     * @param nums
     * @param target
     * @return
     */
    public int solution1(int[] nums, int target) {
        int k = 0; // [0, k) 不是target 的区间
        for (int i = 0 , len = nums.length ; i < len ; i++) {
            if (nums[i] != target) {
                nums[k] = nums[i];
                k++;
            }
        }
        for (int i = k ; i < nums.length ; i ++) {
            nums[k] = -1;
        }
        return k;
    }
}
