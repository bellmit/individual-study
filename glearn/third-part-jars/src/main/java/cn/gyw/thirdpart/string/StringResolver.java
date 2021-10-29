package cn.gyw.thirdpart.string;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @date 2021/10/29 13:27
 */
public class StringResolver {

    /*
    Mac系统换行符："\r"
    Linux系统换行符："\n"
    Windows系统换行符："\r\n"
    */
    private static final String PATTERN_LINE_SEPARATOR = "\\r\\n|\\r|\\n";

    // public static final String PATTERN_LINE_SEPARATOR = "\\R";

    /**
     * 去除收尾空格 和 换行符
     *
     * @return 首尾无空格 && 无换行符的字符串
     */
    public static String trimAndRmLineSeparator(String source) {
        return trim(removeLineSeparator(source));
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