package cn.gyw.thirdpart.utils;


import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /**
     * 字符串小写转大写
     * @param lowerCase lower string
     * @return
     */
    public static String toUpperCase(String lowerCase) {
        return StringUtils.upperCase(lowerCase);
    }

    private StringUtil() {}
}
