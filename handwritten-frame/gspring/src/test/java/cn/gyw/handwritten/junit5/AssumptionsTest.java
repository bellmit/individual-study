package cn.gyw.handwritten.junit5;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

/**
 * 推断测试
 */
public class AssumptionsTest {

	@Test
	void testOnDev() {
		System.setProperty("ENV", "DEV");
		Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
		// remainder of test will proceed
	}

	@Test
	void testOnProd() {
		System.setProperty("ENV", "PROD");
		Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")), "TEST Execution Failed :: ");
		// remainder of test will be aborted
	}
}
