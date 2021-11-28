package cn.gyw.components.web.exceptions;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 异常断言
 */
public interface IExceptionAssert {

    BaseException newException(String message);

    BaseException newException(Throwable t, String message);

    default void assertEmpty(Collection<?> collection, Object... args) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertNotEmpty(Collection<?> collection, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertNotEmpty(String obj, Object... args) {
        if (StringUtils.isEmpty(obj)) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertNull(Object obj, Object... args) {
        if (Objects.nonNull(obj)) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertNotNull(Object obj, Object... args) {
        if (Objects.isNull(obj)) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertTrue(boolean condition, Object... args) {
        if (!condition) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertFalse(boolean condition, Object... args) {
        if (condition) {
            throw newException(Objects.isNull(args) ? "" : Arrays.toString(args));
        }
    }

    default void assertAllNotNull(Object obj, String... props) {
        List<String> propList = Arrays.asList(props);
        if (CollectionUtils.isEmpty(propList)) {
            return;
        }
        if (Objects.isNull(obj)) {
            throw newException("[origin object] is null");
        }
        try {
            for (String prop : propList) {
                Object value = FieldUtils.readDeclaredField(obj, prop, true);
                if (Objects.isNull(value)) {
                    throw newException("[" + prop + "] is null");
                }
            }
        } catch (IllegalAccessException e) {
            throw newException("assertAllNotNull|" + e.getMessage());
        }
    }
}
