package cn.gyw.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间切面
 */
@Aspect
public class TimeAspect implements Ordered {

    /**
     * service 包里的任意方法
     */
    @Pointcut(value = "(execution(* cn.gyw.spring.service..*.*(..))) || (execution(private * cn.gyw.spring.service.impl.*.privateShow(..)))")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前时间戳
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("startTime :" + now);
        Object obj = joinPoint.proceed();
        now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("endTime :" + now);
        return obj;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
