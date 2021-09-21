package cn.gyw.glearn.algorithm.arrays;

/**
 * 面试题 合并两个已经排好序的数组
 * 
 * 归并排序
 */
public class MergeTwoSortedArray {

	public int[] solution(int[] arr1, int[] arr2) {
		int[] temp = new int[arr1.length + arr2.length];

		int i = 0;
		int j = 0;
		int cur = 0;
		while (true) {
			if (arr1[i] < arr2[j]) {
				temp[cur++] = arr1[i];
				i++;
			}
			if (arr1[i] > arr2[j]) {
				temp[cur++] = arr2[j];
				j++;
			}
			if (arr1[i] == arr2[j]) {
				temp[cur++] = arr1[i];
				temp[cur++] = arr2[j];
				i++;
				j++;
			}
			if (i > arr1.length || j > arr2.length) {
				break;
			}
		}
		if (i > arr1.length) {
			for (; j < arr2.length; j++) {
				temp[cur++] = arr2[j];
			}
		}
		if (j > arr2.length) {
			for (; i < arr1.length; i++) {
				temp[cur++] = arr1[i];
			}
		}
		return temp;
	}

}
