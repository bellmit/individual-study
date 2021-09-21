package cn.gyw.handwritten.junit5;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Junit5 学习
 * 
 * 特点：
 * 1. 使用java8 新特性
 * 2. 为描述、组织和执行测试添加了新功能
 * 3. 拆分组件，可以按需引入
 * 4. 支持多个扩展，便于自定义扩展
 * 
 * 说明：
 * vintage 可以将现有Junt4 在Junit5 环境下可用
 * 运行时路径中包含 junit-vintage-engine 工件
 */
@Tag("dev")
public class DevEnvironmentTest {
	
	/**
	 * @Test 注解不再有参数，每个参数都被移到了一个函数中
	 */
	@Test
	public void testThrowsException() {
		Assertions.assertThrows(Exception.class, () -> {
			// 会抛出异常的逻辑
			throw new Exception("this is exception!");
		});
	}
	
	@Test
	public void testTimeout() {
		Assertions.assertTimeout(Duration.ofMillis(1000L), () -> {
			Thread.sleep(10);
		});
	}
	
	@Test
	@Disabled
	public void testIgnore() {
		System.out.println("Junit @Ignore");
	}
	
	@BeforeEach
	public void beforeEachMethod() {
		System.out.println("@BeforeEach");
	}
	
	@AfterEach
	public void afterEachMethod() {
		System.out.println("@AfterEach");
	}
	
	@BeforeAll
	public static void beforeAllMethod() {
		System.out.println("@BeforeAll");
	}
	
	@AfterAll
	public static void afterAllMethod() {
		System.out.println("@AfterAll");
	}
}
