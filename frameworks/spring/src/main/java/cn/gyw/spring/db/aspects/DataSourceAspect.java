package cn.gyw.spring.db.aspects;

import cn.gyw.spring.db.DataSourceUsage;
import cn.gyw.spring.db.DataSourceType;
import cn.gyw.spring.db.route.DynamicDataSourceEntry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 数据源切面
 */
@Aspect
@Order(0) // 优先注入，需早于@Transactional
public class DataSourceAspect {

    private Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    /*
     * 抽取公共切点表达式
     * 1、本类引用
     * 2、其他类的切点（全类名路径）
     * */
    @Pointcut("execution(public * cn.gyw.spring.service.*.*(..))")
    public void pointCut(){}

    // 切入时机：目标方法之前；切入表达式：参数
//    @Before("public int cn.gyw.spring.service.MathCalculator.*(..)")
    @Before("pointCut()")
    public void beforeInvoke(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.debug("方法签名：{}, 参数：{}", joinPoint.getSignature().getName(), Arrays.asList(args));

        /** 先查找方法上的注解，没有的话再去查找类上的注解
         *-----------------------------------------------------------------------
         * 这里使用的是接口的模式，注解在实现类上，所以不能使用如下方式获取目标方法的对象，
         * 因为该方式获取的是该类的接口或者顶级父类的方法的对象.
         * MethodSignature methodSignature = (MethodSignature)point.getSignature();
         * Method method = methodSignature.getMethod();
         * DynamicRoutingDataSource annotation = method.getAnnotation(DynamicRoutingDataSource.class);
         * 通过上面代码是获取不到方法上的注解的，如果真要用上面代码来获取，可以修改aop代理模式，修改为cglib代理
         * 在xml配置文件修改为<aop:aspectj-autoproxy proxy-target-class="true" /> ，
         * proxy-target-class属性true为cglib代理，默认false为jdk动态代理 。
         * ---------------------------------------------------------
         * 本文使用是jdk动态代理， 这里使用反射的方式获取方法
         */
        Class<?> clz = joinPoint.getTarget().getClass();
        Method[] methods = clz.getMethods();
        String currentMethod = joinPoint.getSignature().getName();
        DataSourceUsage anno = null;
        for (Method me : methods) {
            if (me.getName().equals(currentMethod)) {
                anno = me.getAnnotation(DataSourceUsage.class);
            }
        }
        if (Objects.isNull(anno)) {
            anno = clz.getAnnotation(DataSourceUsage.class);
        }
        if (Objects.isNull(anno)) {
            log.debug("保持默认数据源");
            return ;
        }

        DataSourceType type = anno.value();
        // 切换数据源
        DynamicDataSourceEntry.setGlobal(type);
        log.debug("切换至数据源类型:{}", type);
    }

    //    @Before("public int cn.gyw.spring.service.MathCalculator.*(..)")
    @After("pointCut()")
    public void afterInvoke(JoinPoint joinPoint) {
        log.debug("重置数据源类型");
        DynamicDataSourceEntry.resetGlobal();
    }

    // JoinPoint 一定要放在第一个参数，returning : 指定结果的接收字段
    @AfterReturning(value = "cn.gyw.spring.db.aspects.DataSourceAspect.pointCut()", returning = "result")
    public void invokeReturn(JoinPoint joinPoint, Object result) {
        log.debug("方法签名：{}, 返回值：{}", joinPoint.getSignature().getName(), result);
    }

    // throwing : 异常的接收字段
    @AfterThrowing(value = "cn.gyw.spring.db.aspects.DataSourceAspect.pointCut()", throwing = "exception")
    public void invokeException(JoinPoint joinPoint, Exception exception) {
        log.debug("方法签名：{}, 异常：{}", joinPoint.getSignature().getName(), exception);
    }
}
