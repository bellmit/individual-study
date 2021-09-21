package cn.gyw.corejava.jdk;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * JDK7 之前的时间使用测试
 */
public class Jdk7TimeAPITest {

    @Test
    public void getTimeStamp() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2019-2-1");
        long time = date.getTime();
        System.out.println("time : " + time);
        // java.sql.Timestamp
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("timestamp : " + timestamp);
    }

    /**
     * java.util.Date 和 java.sql.Timestamp 之间的互转
     */
    @Test
    public void shouldDateConvertWithTimestamp() {
        // 1.java.util.Date 转 java.sql.Timestamp
        Date date = new Date();
        long time = date.getTime();
        System.out.println("time : " + time);
        // java.sql.Timestamp
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("timestamp : " + timestamp);

        // 2.java.sql.Timestamp 转 java.util.Date
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        long time1 = timestamp1.getTime();
        System.out.println("time1 : " + time1);
        Date date1 = new Date(time1);
        System.out.printf("timestamp 转 date : " + date1);
    }

    /**
     * SimpleDateFormat 非线程安全，在多线程环境下，作为共享变量，导致线程安全问题
     *
     * 解决方法：
     * 1.各线程间不共享SimpleDateFormat，在使用到的时候再创建（可能会大量的创建销毁对象）
     * 2.对SimpleDateFormat进行同步处理；
     * 3.使用ThreadLocal
     * 4.使用线程安全的第三方类库，例如：Apache commons 里的FastDateFormat，Joda-Time类库等
     *
     * 参考：https://www.jianshu.com/p/0b1fa05411fe
     */
    @Test
    public void showSimpleDateFormatNotThreadSafe() {
        // 问题再现
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> dateStringList = new ArrayList<>();
        		/*Lists.newArrayList(
                "2019-4-14 12:00:00",
                "2019-4-14 13:00:00",
                "2019-4-14 14:00:00",
                "2019-4-14 15:00:00",
                "2019-4-14 16:00:00",
                "2019-4-14 17:00:00"
        );*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String dateStr : dateStringList) {
            executorService.execute(() -> {
                try {
                    sdf.parse(dateStr);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 线程执行时，才创建SimpleDateFormat 对象，以避免线程安全
     */
    @Test
    public void userSimpleDateFormatInMethod() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("格式化时间：" + sdf.format(date));
    }

    /**
     * 获取一年（一个月、一周、一天）后的时间
     *
     * 通过 Calendar 来获取
     */
    @Test
    public void showDateAfterYearOrMonthOrDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        calendar.setTime(date);
        //增加一天
        calendar.add(Calendar.DAY_OF_YEAR,1);
        //增加一天
        calendar.add(Calendar.DAY_OF_MONTH,1);
        //增加一天
        calendar.add(Calendar.DAY_OF_WEEK,1);
        //增加一天
        calendar.add(Calendar.DATE,1);
        //Sat Dec 08 09:50:20 CST 2018
        //今天12-04 结果是12-08
        System.out.println(calendar.getTime());

        //增加一周
        calendar.add(Calendar.WEEK_OF_YEAR,1);
        //增加一周
        calendar.add(Calendar.WEEK_OF_MONTH,1);
        //Sat Dec 22 09:54:34 CST 2018
        //12-22
        System.out.println(calendar.getTime());

        //增加一个月
        calendar.add(Calendar.MONTH,1);
        //Tue Jan 22 09:57:38 CST 2019
        //2019-1-22
        System.out.println(calendar.getTime());

        //增加一年
        calendar.add(Calendar.YEAR,1);
        //Wed Jan 22 09:57:38 CST 2020
        //2020-1-22
        System.out.println(calendar.getTime());
    }

    /**
     * 使用DateUtils 工具
     */
    @Test
    public void userDateUtilsTruncDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 获取一年后今天的零点
        calendar.add(Calendar.YEAR, 1);
        Calendar cl = Calendar.getInstance();
        		/*DateUtils.truncate(calendar, Calendar.DATE);*/
        Date afterYearDay = cl.getTime();
        System.out.println("day of year after : " + afterYearDay);
    }
    
    @Test
    public void calendarTimezone() {
    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-0"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(calendar.getTime());
		System.out.println(">>" + dateStr);
    }
}
