package cn.gyw.backend.components.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 日期工具
 *
 * @date 2021/11/9 18:44
 */
public final class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取前一天的日期
     *
     * @return 字符串
     */
    public static LocalDate getDateOfYesterday() {
        return LocalDate.now().minusDays(1);
    }

    /**
     * 获取前一天的日期
     *
     * @return 字符串
     */
    public static String formatDate(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return localDate.format(dateFormatter);
    }

    private DateUtil() {
    }
}
