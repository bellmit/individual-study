package cn.gyw.corejava.junit.custom;

import cn.gyw.corejava.junit.JunitIntercepter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间拦截器
 */
public class JunitTimeInterceptor implements JunitIntercepter {

    private LocalDateTime startDateTime;

    private DateTimeFormatter millFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void interceptBefore() {
        startDateTime = LocalDateTime.now();
        System.out.println("start at :" + millFormatter.format(startDateTime));
    }

    @Override
    public void interceptAfter() {
        LocalDateTime end = LocalDateTime.now();
        System.out.println("end at :" + millFormatter.format(end)
                + ", total cust :" + Duration.between(startDateTime, end));

    }
}
