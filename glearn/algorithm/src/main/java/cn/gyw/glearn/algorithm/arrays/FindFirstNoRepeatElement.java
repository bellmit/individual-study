package cn.gyw.glearn.algorithm.arrays;

import java.util.HashSet;
import java.util.Set;

/**
 * 面试题 查找第一个没有重复的元素
 */
public class FindFirstNoRepeatElement {

	/**
	 * 时间复杂度 O(n^2)
	 * @param data
	 * @return
	 */
	public int solution(int[] data) {
		Set<Integer> repeated = new HashSet<>();
		boolean isRepeated;
		for (int i = 0, len = data.length ; i < len; i++) {
			isRepeated = false;
			int cur = data[i];
			for (int j = data.length - 1 ; j > i ; j --) {
				if (data[j] == cur) {
					repeated.add(data[j]);
					isRepeated = true;
					break;
				}
			}
			if (!isRepeated && !repeated.contains(cur)) {
				return cur;
			}
		}
		return -1;
	}
}
