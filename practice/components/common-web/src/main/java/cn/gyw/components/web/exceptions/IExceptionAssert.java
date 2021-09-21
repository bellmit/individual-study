package cn.gyw.components.web.exceptions;

/**
 * 异常断言
 */
public interface IExceptionAssert {
	
	BaseException newException(Object... args);

	BaseException newException(Throwable t, Object... args);

	default void assertNotNull(Object obj, Object... args) {
		if (obj == null) {
			throw newException(args);
		}
	}
	
	default void assertTrue(boolean condition, Object... args) {
		if (!condition) {
			throw newException(args);
		}
	}
}
