package cn.gyw.platform.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 */
public final class DateUtil {

    private static final DateTimeFormatter LONG_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String currentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return LONG_FORMATTER.format(now);
    }

    private DateUtil() {}
}
