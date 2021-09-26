package cn.gyw.platform.gorm.framework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 类映射
 */
public class ClassMappings {

    private static final Set<Class<?>> SUPPORTED_SQL_OBJECTS = new HashSet<>();

    static {
        // 支持自动类型转换的类
        Class<?>[] classes = {
                boolean.class, Boolean.class,
                short.class, Short.class,
                String.class,
                Date.class,
                Timestamp.class,
                BigDecimal.class
        };
        SUPPORTED_SQL_OBJECTS.addAll(Arrays.asList(classes));
    }

    static boolean isSupportedSQLObject(Class<?> clazz) {
        return clazz.isEnum() || SUPPORTED_SQL_OBJECTS.contains(clazz);
    }


    public static <T> Map<String, Method> findPublicGetters(Class<T> entityClass) {
        return null;
    }

    public static <T> Map<String, Method> findPublicSetters(Class<T> entityClass) {
        return null;
    }

    public static <T> Field[] findFields(Class<T> entityClass) {
        return null;
    }
}
