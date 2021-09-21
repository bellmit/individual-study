package cn.gyw.spring.db.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务切面
 */
@Aspect
public class TransactionAspect {

    private Logger log = LoggerFactory.getLogger(TransactionAspect.class);

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Pointcut("execution(public * cn.gyw.spring.service.*.*(..))")
    public void pointCut(){}

    public void before() {
        System.out.println("Method before...");
    }

    // 环绕拦截
    @Around(value = "pointCut()")
    public Object invokeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("当前事务名称：{}", txName);
        // 执行原方法
        Object result = joinPoint.proceed();

        return result;
    }
}
