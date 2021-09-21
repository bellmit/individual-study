package cn.gyw.thirdpart.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * 从1970年1月1日0时0分0秒到现在的毫秒数
     */
    public static long getCurrentTimeMillis() {
        return Clock.systemDefaultZone().millis();
    }

    /**
     * 取得年月日、小时分钟秒
     */
    public static int getCurrentTimeJDK8(String particle) {
        LocalDateTime dt = LocalDateTime.now();
        switch (particle) {
            case "year" :
                dt.getYear();
                break;
            case "month" :
                dt.getMonthValue();
                break;
            case "day" :
                dt.getDayOfMonth();
                break;
            case "hour" :
                dt.getHour();
                break;
            case "min" :
                dt.getMinute();
                break;
            case "sec" :
                dt.getSecond();
                break;
        }
        return -1;
    }

    public static String getCurrentTimeBeforeJDK8(String particle) {
        Calendar calendar = Calendar.getInstance();
        switch (particle) {
            case "year" :
                calendar.get(Calendar.YEAR);
                break;
            case "month" :
                calendar.get(Calendar.MONTH);
                break;
            case "day" :
                calendar.get(Calendar.DATE);
                break;
            case "hour" :
                calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case "min" :
                calendar.get(Calendar.MINUTE);
                break;
            case "sec" :
                calendar.get(Calendar.SECOND);
                break;
        }
        return null;
    }

    /**
     * 转换时间字符串 为 Date
     * @param dateStr 时间字符串
     * @param isHand 是否需要减去1秒
     * @return Date
     */
    public static Date checkDate(String dateStr, boolean isHand) {
        try {
            return ParLongDateFormat.parse(dateStr);
        } catch (Exception e) {
        }
        try {
            Date tmp = ParDateFormat.parse(dateStr);
            if (isHand)
                tmp = DateUtils.addSeconds(DateUtils.addDays(tmp, 1), -1);
            return tmp;
        } catch (Exception e) {
        }
        try {
            Date tmp = ParMonthTimeDateFormat.parse(dateStr);
            if (isHand)
                tmp = DateUtils.addSeconds(DateUtils.addMonths(tmp, 1), -1);
            return tmp;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 制定转换格式，转换时间字符串
     * @param date
     * @param dateFormat
     * @return
     */
    public static String format(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    protected static final FastDateFormat DateFormat = FastDateFormat
            .getInstance("yyyy-MM-dd");
    protected static final SimpleDateFormat ParDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    protected static final FastDateFormat LongDateFormat = FastDateFormat
            .getInstance("yyyy-MM-dd HH:mm:ss");
    protected static final SimpleDateFormat ParLongDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    protected static final FastDateFormat ShortTimeDateFormat = FastDateFormat
            .getInstance("HH:mm");
    protected static final SimpleDateFormat ParShortTimeDateFormat = new SimpleDateFormat(
            "HH:mm");

    protected static final FastDateFormat MonthTimeDateFormat = FastDateFormat
            .getInstance("yyyy-MM");
    protected static final SimpleDateFormat ParMonthTimeDateFormat = new SimpleDateFormat(
            "yyyy-MM");
}
