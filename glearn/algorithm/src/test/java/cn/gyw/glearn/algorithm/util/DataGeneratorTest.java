package cn.gyw.glearn.algorithm.util;

import org.junit.Test;

public class DataGeneratorTest {

	@Test
	public void testGenerateIntArray() {
		int[] arr = DataGenerator.generateRandomArray(10, 1, 100_000_000);
		for (int i = 0 ; i < arr.length ; i ++) {
			System.out.println(">>" + arr[i]);
		}
	}
}
