package cn.gyw.glearn.algorithm.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * 数学计算
 *
 */
public class MathTest {

	/**
	 * 求余
	 */
	@Test
	public void mod() {
		int mockArrayLength;
		for (int i = 1 ; i < 10 ; i ++) {
			mockArrayLength = (int) Math.pow(2, i);
			// 假设数组的长度是2的倍数，验证HashMap 数组角标的算法
			System.out.println("2^" + i + "=" + mockArrayLength);
			Assert.assertEquals( 100 % mockArrayLength, 100 & (mockArrayLength - 1));
		}
	}
}
