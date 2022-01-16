package cn.gyw.backend.components.common.utils;

public final class CommonUtil {

    /**
     * 首字母小写
     * @param name
     * @return
     */
    public static String toFirstLower(String name) {
        char[] chars = name.toCharArray();
        if (chars[0] > 96) { // 小写字母开头
            return name;
        }
        chars[0] += 32;
        return new String(chars);
    }
}
