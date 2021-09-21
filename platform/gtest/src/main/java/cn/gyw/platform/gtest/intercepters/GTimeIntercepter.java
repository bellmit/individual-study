package cn.gyw.platform.gtest.intercepters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 运行时间拦截器
 */
public class GTimeIntercepter implements GIntercepter {

    private final Logger log = LoggerFactory.getLogger(GTimeIntercepter.class);

    private LocalDateTime startDateTime;

    private DateTimeFormatter millFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void doBefore() {
        startDateTime = LocalDateTime.now();
        log.info("start at:[{}]", millFormatter.format(startDateTime));
    }

    @Override
    public void doAfter() {
        LocalDateTime end = LocalDateTime.now();
        log.info("end at:[{}], total cost :[{}]", millFormatter.format(end), Duration.between(startDateTime, end));
    }
}
