package cn.gyw.glearn.algorithm;

/**
 * 字符串相关的问题
 */
public class StringQuestions {

	/**
	 * 查找指定字符串中出现频率最高的字符
	 * 
	 * 若存在多个字符，返回第一个
	 * 
	 * @param target
	 * @return
	 */
	public char findMostChar(String target) {
		System.out.println(">>" + target);
		char[] chars = target.toCharArray();

		int[] data = new int[256];
		int index = 0;
		char mostChar = 0;
		int loc = 0;
		for (int i = 0, len = chars.length; i < len; i++) {
			// char -> ASCII 码值
			loc = Integer.valueOf(chars[i]);
			data[loc] += 1; // 频率+1
			if (data[index] < data[loc]) {
				index = loc;
				mostChar = chars[i];
			}
		}
		return mostChar;
	}

	/**
	 * 从指定字符串中寻找没有重复字母的最长子串
	 * 
	 * 例如： abcabcbb -> abc <br>
	 * bbbb -> b
	 * 
	 * 解决思路：滑动窗口
	 * 
	 * @return
	 */
	public String findLongestUniqueSubString(String source) {
		// [l, r] 闭区间，显示子串
		int l = 0;
		int r = -1;
		int[] freq = new int[256];
		char[] chars = source.toCharArray();
		int res = 0;
		int[] section = new int[2]; // 最大区间字符串
		
		while (l < source.length()) {
			if (r + 1 < source.length() && freq[chars[r + 1]] == 0) {
				freq[chars[++r]]++;
			} else {
				freq[chars[l++]]--;
			}
			
			if (res < (r - l + 1)) { // 大子串更新result
				res = r - l + 1;
				section[0] = l;
				section[1] = r;
			}
		}
		
		return source.substring(section[0], section[1] + 1);
	}
}
