package cn.gyw.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 日志切面类
 *
 * @Aspect 指定当前类是切面类
 */
@Aspect
public class LogAspect {

    /*
    * 抽取公共切点表达式
    * 1、本类引用
    * 2、其他类的切点（全类名路径）
    * */
    @Pointcut("execution(public int cn.gyw.spring.service.MathCalculator.add(..))")
    public void pointCut(){}

    // 切入时机：目标方法之前；切入表达式：参数
//    @Before("public int cn.gyw.spring.service.MathCalculator.*(..)")
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() + "[前置通知]计算运行");
    }

//    @Before("public int cn.gyw.spring.service.MathCalculator.*(..)")
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "[后置通知]计算结束...");
    }

    // JoinPoint 一定要放在第一个参数，eturning : 指定结果的接收字段
    @AfterReturning(value = "cn.gyw.spring.aop.aspect.LogAspect.pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "[Return]计算正常返回... 运行结果：" + result);
    }

    // throwing : 异常的接收字段
    @AfterThrowing(value = "cn.gyw.spring.aop.aspect.LogAspect.pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println(joinPoint.getSignature().getName() + "计算异常...异常信息：" + exception);
    }
}
