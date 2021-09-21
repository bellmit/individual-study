package cn.gyw.glearn.algorithm.util;

import java.util.Random;

/**
 * 数据生成器
 */
public final class DataGenerator {

	private static Random random = new Random();

	/**
	 * 生成随机整型数组
	 *
	 * @param n 数组个数
	 * @return
	 */
	public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
		assert n > 0 && rangeL < rangeR;
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			// [0 - 10000000 ]
			arr[i] = random.nextInt(rangeR - rangeL + 1) + rangeL;
		}
		return arr;
	}

	/**
	 * 生成有序整数数组
	 *
	 * @param n
	 * @return
	 */
	public static int[] generateOrderedArray(int n) {
		assert n > 0;
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i;
		}
		return arr;
	}

	/**
	 * 生成指定长度的字符串
	 * @param len
	 * @return
	 */
	public static String generateString(int len) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
