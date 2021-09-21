package cn.gyw.corejava.jdk;

import cn.gyw.corejava.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * JDK8 时间 API 测试学习
 *
 * java.time包中的是类是不可变且线程安全的。新的时间及日期API位于java.time中，下面是一些关键类
 *
 * ●Instant——它代表的是时间戳
 *
 * ●LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日，入职日期等。
 *
 * ●LocalTime——它代表的是不含日期的时间
 *
 * ●LocalDateTime——它包含了日期及时间，不过还是没有偏移信息或者说时区。
 *
 * ●ZonedDateTime——这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的
 * 
 * Duration
 * 
 * Period
 */
public class Jdk8TimeAPITest extends AbstractTest {
	
	/**
	 * 时间差
	 */
	@Test
	public void timeSub() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate date1 = LocalDate.parse("20210110", formatter);
		LocalDate date2 = LocalDate.parse("20210115", formatter);
		
		System.out.println("是否之前：" +Period.ofDays(10));
		System.out.println("是否之前：" +Period.between(date1, date2));
		System.out.println("是否之前：" + date1.isBefore(date2));
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HHmmss");
		LocalTime time1 = LocalTime.parse("052750", formatter2);
		LocalTime time2 = LocalTime.parse("052933", formatter2);
		// 必须要到秒
		System.out.println(Duration.between(time1, time2));
		System.out.println("相差秒数：" + Duration.between(time1, time2).getSeconds());
		System.out.println("相差分钟数：" + Duration.between(time1, time2).toMinutes());
		System.out.println("相差小时数：" + Duration.between(time1, time2).toHours());
	}
	
	/**
	 * 时间比较
	 */
	@Test
	public void timeCompare() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		LocalTime time1 = LocalTime.parse("052750", formatter);
		LocalTime time2 = LocalTime.parse("052933", formatter);
		
		long s1 = time1.getLong(ChronoField.SECOND_OF_DAY);
		long s2 = time2.getLong(ChronoField.SECOND_OF_DAY);
		
		System.out.println(s1 + ">>>" + s2);
	}
	
	/**
	 * 时区API
	 * 
	 * 带时区的时间为分别为：ZonedDate、ZonedTime、ZonedDateTime
	 */
	@Test
	public void timezone() {
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC+0"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("世界统一时间：" + formatter.format(localDateTime));
		
		LocalDateTime now1 = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println("美国洛杉矶时间：" + now1);
	}
	

    /**
     * 日期API
     */
    @Test
    public void localDate() {
        // 获取当前日期
        LocalDate localDate = LocalDate.now();
        System.out.println("当前日期：" + localDate);
        // 获取年月日
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        Assert.assertEquals(2019, year);
        Assert.assertEquals(4, month);
        // 获取特定的日期
        LocalDate specialDate = LocalDate.of(1993, 4, 27);
        System.out.println(specialDate);
        // 比较日期是否相等
        Assert.assertFalse(localDate.equals(specialDate));
        // 判断重复日期
        MonthDay birthDay = MonthDay.of(specialDate.getMonth(), specialDate.getDayOfMonth());
        MonthDay currDay = MonthDay.from(localDate);
        Assert.assertFalse(birthDay.equals(currDay));
    }

    /**
     * 日期、日期时间 修改
     */
    @Test
    public void catchYearLastDay() {
        // 获取某一年的最后一天的日期

        // 日期时间修改
/*        LocalDateTime now = LocalDateTime.now();
        LocalDateTime addDay = now.plusDays(1);
        System.out.println("一天后：" + addDay);
        LocalDateTime addMonth = now.plusMonths(1);
        System.out.println("一月后：" + addMonth);
        LocalDateTime addYear = now.plusYears(1);
        System.out.println("一年后：" + addYear);*/

        // 获取七天内的日期
        int dayChange = 7;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        for (; dayChange > 0 ; dayChange --) {
            LocalDate chageDate = currentDate.plusDays(dayChange);
            System.out.println("======================================================================\n"
                    + chageDate.format(formatter));
        }
    }

    /**
     * 日期、时间格式化
     */
    @Test
    public void dateFormat() {
        // 获取时间预定义格式，DateTimeFormatter 预定义了很多格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        // 获取当前日期时间
        LocalDate now = LocalDate.now();
        // 指定格式化器格式化时间
        String nowStr = now.format(dateTimeFormatter);
        System.out.println("nowStr : " + nowStr);

        // 自定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String nowStr2 = now.format(formatter);
        System.out.println("nowStr2 : " + nowStr2);

        // 将字符串转换为日期
        LocalDate date = LocalDate.parse(nowStr2, formatter);
        System.out.printf("date string conver to date:%s", date);
        System.out.println();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd _HHmmss_SSS");
        String result = LocalDateTime.now(ZoneOffset.UTC).format(dateFormatter);
        System.out.println("UTC:" + result);
    }
    
    /**
     * 获取当前时间戳
     */
    @Test
    public void currentTimestamp() {
    	// 当前时间戳，单位秒
    	System.out.println("当前时间戳(s)：" + Instant.now().getEpochSecond());
    }
    
}
