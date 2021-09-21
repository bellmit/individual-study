package cn.gyw.glearn.algorithm.sort;

import java.util.Arrays;
import java.util.Stack;

/**
 * 快速排序 对冒泡排序算法的一种改进
 * <p>
 * 通过一趟排序，将一组数据分割为独立的两个部分，其中一部分的数据比另一部分的所有数据都要小 然后再按此方法对两部分数据，以同样的方式分割，直至序列有序
 * <p>
 * 时间复杂度：
 */
public class QuickSorter implements ISorter {

	public static void main(String[] args) {
		QuickSorter quickSorter = new QuickSorter();
		int[] data = new int[] { 3, 1, 2, 4, 5, 7, 10, 6 };
//		quickSorter.quickSort(data, 0, data.length - 1);
		quickSorter.quickSort2(data, 0, data.length - 1);

		System.out.println("Sorted:" + Arrays.toString(data));
	}

	/**
	 * 快速排序
	 *
	 * @param data
	 * @param low
	 * @param high
	 */
	private void quickSort(int[] data, int low, int high) {
		if (low >= high) {
			return;
		}
		int pivot = data[low], left = low, right = high;
		while (left < right) {
			// 从右向左遍历找到第一个小于的数停止
			while (left < right && data[right] >= pivot) {
				right--;
			}

			// 从左到右，找第一个大的数
			while (left < right && data[left] <= pivot) {
				left++;
			}

			// 交换两个值
			if (left < right) {
				swap(data, left, right);
			}
		}
		// 这里的data[left]一定小于p，经过left、right交换后left处的值一定小于p
		data[low] = data[left];
		data[left] = pivot;
		quickSort(data, low, left - 1);
		quickSort(data, left + 1, high);
	}

	/**
	 * 非递归
	 */
	public void quickSort2(int[] data, int low, int high) {
		int pivot;
		if (low >= high) {
			return;
		}
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(low);
		stack.push(high);
		while (!stack.empty()) {
			// 先弹出high,再弹出low
			high = stack.pop();
			low = stack.pop();
			pivot = data[low];
			
			int left = low, right = high;
			while (left < right) {
				// 从右向左遍历找到第一个小于的数停止
				while (left < right && data[right] >= pivot) {
					right--;
				}

				// 从左到右，找第一个大的数
				while (left < right && data[left] <= pivot) {
					left++;
				}

				// 交换两个值
				if (left < right) {
					swap(data, left, right);
				}
			}
			// 这里的data[left]一定小于p，经过left、right交换后left处的值一定小于p
			data[low] = data[left];
			data[left] = pivot;
			
			// 先压low,再压high
			if (low < left - 1) {
				stack.push(low);
				stack.push(left - 1);
			}
			if (left + 1 < high) {
				stack.push(left + 1);
				stack.push(high);
			}
		}
	}

	/**
	 * 交换数组两下标的位置
	 *
	 * @param data 数组
	 * @param i
	 * @param j
	 */
	private void swap(int[] data, int i, int j) {
		int tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	@Override
	public void sort(int[] data) {
		// 写法一
		// qsort(0, data.length - 1, data);

		// 写法二

	}

	@Override
	public int getRunCnt() {
		return 0;
	}
}
