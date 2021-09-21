package cn.gyw.mybatis.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Objects;

/**
 * 分表
 */
public class SubTableStrategy {

    private final static DateTimeFormatter secFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String tableIndex(String date) {
        // 一天毫秒数
        long millsOfDay = 86400000;
        // 东八区的毫秒值（8小时）
        long timeZoneMills = 8 * 3600000;
        // 一年分区表数
        long numberOfTable = 390;
        // 间隔
        long inerval = 1 * millsOfDay;

        long currentMills = 0;
        if (Objects.nonNull(date)) {
            LocalDate localDate = LocalDate.parse(date);
            currentMills = LocalDateTime.of(localDate, LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        } else {
            // 带时区的时间
            ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.of("+8"));
            currentMills = dateTime.toInstant().toEpochMilli();
        }

        long seq = currentMills / inerval % numberOfTable;
        return String.valueOf(seq);
    }

    public static void main(String[] args) {
        System.out.println(24 * 60 * 60 * 1000);
        String date = LocalDateTime.now().format(secFormatter);
        long ret = secFormatter.parse(date).getLong(ChronoField.MILLI_OF_DAY);
        System.out.println(">>" + ret);

        ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.of("+8"));
        ret = dateTime.getLong(ChronoField.MILLI_OF_DAY);
        System.out.println(">>" + ret);

        System.out.println(tableIndex("2018-04-20"));
        System.out.println(tableIndex("2019-04-21"));
        System.out.println(tableIndex("2017-04-22"));
        System.out.println(tableIndex("2020-04-23"));
    }

}
