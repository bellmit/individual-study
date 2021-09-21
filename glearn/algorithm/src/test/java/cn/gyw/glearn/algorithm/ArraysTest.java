package cn.gyw.glearn.algorithm;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import cn.gyw.glearn.algorithm.arrays.FindFirstNoRepeatElement;
import cn.gyw.glearn.algorithm.arrays.FindSecondMinElement;
import cn.gyw.glearn.algorithm.arrays.MergeTwoSortedArray;

/**
 * 
 */
public class ArraysTest {

	private int[] data;

	private Object result;

	@Test
	public void findSecondMinElement() {
		data = new int[] { 3, 2, 1, 0, 5, 3, -1 };
		FindSecondMinElement findSecondMinElement = new FindSecondMinElement();
		result = findSecondMinElement.solution(data);
	}

	@Test
	public void findFirstNoRepeatElement() {
		data = new int[] { 2, 2, 1, 3, 3 };
		FindFirstNoRepeatElement findFirstNoRepeatElement = new FindFirstNoRepeatElement();
		result = findFirstNoRepeatElement.solution(data);
	}

	@Test
	public void mergeTwoSortedArray() {
		int[] arr1 = { 1, 2, 3 };
		int[] arr2 = { 4, 5 };
		MergeTwoSortedArray mergeTwoSortedArray = new MergeTwoSortedArray();
		result = mergeTwoSortedArray.solution(arr1, arr2);
	}

	@After
	public void printResult() {
		if (result instanceof int[]) {
			Arrays.asList(result).forEach(System.out::println);
		} else {
			System.out.println("result:" + result);			
		}
	}
}
