package cn.gyw.platform.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 */
public final class DateUtil {

    public static final String DATE_TIME_WITH_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_NO_HYPHEN = "yyyyMMddHHmmss";
    public static final String DATE_WITH_HYPHEN = "yyyy-MM-dd";
    public static final String DATE_NO_HYPHEN = "yyyyMMdd";

    /**
     * 获取当前日期
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String currentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_HYPHEN));
    }

    private DateUtil() {
    }
}
