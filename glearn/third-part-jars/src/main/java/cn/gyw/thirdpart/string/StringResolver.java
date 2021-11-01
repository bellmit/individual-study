package cn.gyw.thirdpart.string;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * TODO
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

    // public static final String PATTERN_LINE_SEPARATOR = "\\R";

    /**
     * 去除首尾空格 和 换行符
     *
     * @param obj 目标对象
     * @param props 字段名
     */
    public static void trimAndRemoveLineSeparator(Object obj, String... props) {
        Class<?> clazz = obj.getClass();
        for (String prop : props) {
            Field field = FieldUtils.getField(clazz, prop, true);
            try {
                Object value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }
                field.set(obj, trim(removeLineSeparator(value.toString())));
            } catch (IllegalAccessException e) {
                // 处理失败，不影响现有流程，继续下一个
                log.warn("[{}] 去除首尾空格 && 换行符异常", prop);
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