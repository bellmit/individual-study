package cn.gyw.components.web.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具
 *
 * @date 2021/11/9 18:44
 */
public final class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取前一天的日期
     * @return 字符串
     */
    public static String getDateOfYesterday() {
        return LocalDate.now().minusDays(1).format(dateFormatter);
    }

    private DateUtil() {}
}
