package cn.gyw.platform.gorm;

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

    public static final String PREFIX_METHOD_GET = "get";
    public static final String PREFIX_METHOD_SET = "set";

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
        return findMethods(entityClass, PREFIX_METHOD_GET);
    }

    public static <T> Map<String, Method> findPublicSetters(Class<T> entityClass) {
        return findMethods(entityClass, PREFIX_METHOD_SET);
    }

    private static <T> Map<String, Method> findMethods(Class<T> entityClass, String methodPrefix) {
        Map<String, Method> map = new HashMap<>();
        Method[] methods = entityClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith(methodPrefix)) {
                map.put(trimFieldName(method.getName(), methodPrefix), method);
            }
        }
        return map;
    }

    private static String trimFieldName(String methodName, String methodPrefix) {
        methodName = methodName.replace(methodPrefix, "");
        char[] chars = methodName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }

    public static <T> Field[] findFields(Class<T> entityClass) {
        return entityClass.getDeclaredFields();
    }

    private ClassMappings() {}
}
