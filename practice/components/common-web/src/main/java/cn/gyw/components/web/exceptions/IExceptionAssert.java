package cn.gyw.components.web.exceptions;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 异常断言
 */
public interface IExceptionAssert {
	
	BaseException newException(Object... args);

	BaseException newException(Throwable t, Object... args);

	default void assertEmpty(Collection<?> collection, Object... args) {
		if (CollectionUtils.isNotEmpty(collection)) {
			throw newException(args);
		}
	}

	default void assertNotEmpty(Collection<?> collection, Object... args) {
		if (CollectionUtils.isEmpty(collection)) {
			throw newException(args);
		}
	}

	default void assertNull(Object obj, Object... args) {
		if (Objects.nonNull(obj)) {
			throw newException(args);
		}
	}

	default void assertNotNull(Object obj, Object... args) {
		if (Objects.isNull(obj)) {
			throw newException(args);
		}
	}
	
	default void assertTrue(boolean condition, Object... args) {
		if (!condition) {
			throw newException(args);
		}
	}

	default void assertFalse(boolean condition, Object... args) {
		if (condition) {
			throw newException(args);
		}
	}
}
