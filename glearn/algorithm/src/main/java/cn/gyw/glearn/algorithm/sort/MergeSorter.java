package cn.gyw.glearn.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 * <p>
 * 时间复杂度：O(nlogn)
 */
public class MergeSorter implements ISorter {

	public static void main(String[] args) {
		int[] arr = { 11, 44, 23, 67, 88, 65, 34, 48, 9, 12 };
		int[] tmp = new int[arr.length]; // 新建一个临时数组存放
//		merge_sort(arr);
		merge_sort_recursive(arr, tmp, 0, arr.length - 1);
		
		System.out.println("Sorted>>" + Arrays.toString(arr));
	}

	// 归并排序（Java-迭代版）
	public static void merge_sort(int[] arr) {
		int len = arr.length;
		int[] result = new int[len];
		int block, start;

		// 原版代码的迭代次数少了一次，没有考虑到奇数列数组的情况
		for (block = 1; block < len * 2; block *= 2) {
			for (start = 0; start < len; start += 2 * block) {
				int low = start;
				int mid = (start + block) < len ? (start + block) : len;
				int high = (start + 2 * block) < len ? (start + 2 * block) : len;
				// 两个块的起始下标及结束下标
				int start1 = low, end1 = mid;
				int start2 = mid, end2 = high;
				// 开始对两个block进行归并排序
				while (start1 < end1 && start2 < end2) {
					result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
				}
				while (start1 < end1) {
					result[low++] = arr[start1++];
				}
				while (start2 < end2) {
					result[low++] = arr[start2++];
				}
			}
			int[] temp = arr;
			arr = result;
			result = temp;
		}
		result = arr;
	}

	// 归并排序（Java-递归版）
	static void merge_sort_recursive(int[] arr, int[] result, int start, int end) {
		if (start >= end)
			return;
		int len = end - start, mid = (len >> 1) + start;
		
		merge_sort_recursive(arr, result, start, mid);
		merge_sort_recursive(arr, result, mid + 1, end);
		
		merge(arr, start, mid, end);
	}
	
	private static void merge(int[] array, int left, int mid, int right) {
		int[] temp = new int[right - left + 1];
		int i = 0;
		int p1 = left;
		int p2 = mid + 1;
		// 比较左右两部分的元素，哪个小，把那个元素填入temp中
		while (p1 <= mid && p2 <= right) {
			temp[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
		}
		// 上面的循环退出后，把剩余的元素依次填入到temp中
		// 以下两个while只有一个会执行
		while (p1 <= mid) {
			temp[i++] = array[p1++];
		}
		while (p2 <= right) {
			temp[i++] = array[p2++];
		}
		// 把最终的排序的结果复制给原数组
		for (i = 0; i < temp.length; i++) {
			array[left + i] = temp[i];
		}
	}

	@Override
	public void sort(int[] data) {
		mergeSort(data, data.length);
	}

	/**
	 * 自底向上的归并排序 不需要递归
	 *
	 * @param arr
	 * @param n
	 */
	private void mergeSort(int[] arr, int n) {
		int[] aux = new int[n];
		System.arraycopy(arr, 0, aux, 0, arr.length);

		for (int sz = 1; sz < n; sz += sz) {
			for (int i = 0; i < n; i += sz + sz) {
				merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), aux);
			}
		}
	}

	private void merge(int[] arr, int l, int mid, int r, int aux[]) {
		int i = l, j = mid + 1;
		for (int k = l; k <= r; k++) {
			if (i > mid) {
				arr[k] = aux[j];
				j++;
			} else if (j > r) {
				arr[k] = aux[i];
				i++;
			} else if (aux[i] < aux[j]) {
				arr[k] = aux[i];
				i++;
			} else {
				arr[k] = aux[j];
				j++;
			}

		}
	}

	/**
	 * 递归 时间复杂度 O(nlogn)
	 * 
	 * @param arr
	 * @param l
	 * @param r
	 */
	public void mergeSort(int arr[], int l, int r) {
		if (l > r) {
			return;
		}
		int mid = l + (r - l) / 2;
		mergeSort(arr, l, mid);
		mergeSort(arr, mid + 1, r);
		// merge(arr, l, mid, r);
	}

	@Override
	public int getRunCnt() {
		return 0;
	}
}
