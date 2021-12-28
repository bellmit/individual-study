package cn.gyw.thirdpart.string;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 字符串处理类
 *
 * @date 2021/10/29 13:27
 */
public class StringResolver {

    private static final Logger log = LoggerFactory.getLogger(StringResolver.class);

    /*
    Mac系统换行符："\r"
    Linux系统换行符："\n"
    Windows系统换行符："\r\n"
    */
    private static final String PATTERN_LINE_SEPARATOR = "\\r\\n|\\r|\\n";
    // 下划线分隔符
    private static final char SEPARATOR = '_';

    /**
     * 驼峰命名 转 下划线
     */
    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if (Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 下划线命名 转 小驼峰命名
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线命名 转 大写驼峰命名
     *
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 去除首尾空格 和 换行符
     *
     * @param obj   目标对象
     * @param props 字段名
     */
    public static void trimAndRemoveLineSeparator(Object obj, String... props) {
        List<String> propList = Arrays.asList(props);
        if (obj == null || CollectionUtils.isEmpty(propList)) {
            return;
        }
        Class<?> clazz = obj.getClass();
        for (String prop : propList) {
            try {
                Field field = FieldUtils.getField(clazz, prop, true);
                if (Objects.isNull(field)) {
                    log.error("No such field: " + prop);
                    continue;
                }
                Object value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }
                field.set(obj, trim(removeLineSeparator(value.toString())));
            } catch (Exception e) {
                // 处理失败，不影响现有流程，继续下一个
                log.error("[" + prop + "] 去除首尾空格 && 换行符异常", e);
            }
        }
    }

    /**
     * 去除首尾空格
     *
     * @param source 源字符串
     * @return 首尾无空格字符串
     */
    public static String trim(String source) {
        return checkIsEmpty(source) ? source : source.trim();
    }

    /**
     * 去除换行符
     *
     * @param source 源字符串
     * @return 无换行符的字符串
     */
    public static String removeLineSeparator(String source) {
        return checkIsEmpty(source) ? source : source.replaceAll(PATTERN_LINE_SEPARATOR, "");
    }

    private static boolean checkIsEmpty(String source) {
        return StringUtils.isEmpty(source);
    }

    private StringResolver() {
    }
}