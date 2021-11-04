package cn.gyw.corejava.base;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class BaseDataTypeTest {

	@Test
	public void testBigDecimal() {
		BigDecimal v1 = BigDecimal.ZERO;

		System.out.println(v1.compareTo(null));
	}

	/**
	 * 浮点数计算问题
	 */
	@Test
	public void test1() {
		// 0.30000000000000004
		System.out.println(3 * 0.1);
		// 0.6000000000000001
		System.out.println(3 * 0.2);
		// 0.2
		System.out.println(2 * 0.1);
		
		System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
	}
	
	/**
	 * a = a + b 与 a += b 区别
	 * a + b : 不会做类型转换
	 * += ：隐式类型转换
	 */
	@Test
	public void plusTest() {
		byte a = 127;
		byte b = 127;
		//a = a + b; // Type mismatch: cannot convert from int to byte
		a += b;
		
		short c1 = 1;
		//c1 = c1 + 1; // Type mismatch: cannot convert from int to short
		c1 += 1; // <==> c1 = (short) c1 + 1;
		
		System.out.println(a);
	}
}
