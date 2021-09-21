package cn.gyw.glearn.algorithm.arrays;

/**
 * 面试题
 * 查找数组中第二小的元素
 * 
 * @see FindSecondMinElementTest
 */
public class FindSecondMinElement {

	/**
	 * 时间复杂度 O(n)
	 * @param data
	 * @return
	 */
    public int solution(int[] data) {
    	int min = data[0];
    	int sec = data[1];
    	for (int i = 1, len = data.length ; i < len ; i ++) {
    		if (data[i] < min) {
    			sec = min;
    			min = data[i];
    		} else if (data[i] < sec) {
    			sec = data[i];
    		}
    	}
        return sec;
    }

}
