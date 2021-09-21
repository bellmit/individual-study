package cn.gyw.handwritten.gspring.aop.intercept;

/**
 * 方法拦截器
 * 
 * 所有的通知都会转成不同的拦截器，构成方法拦截器链
 */
public interface GMethodInterceptor {

    Object invoke(GMethodInvocation invocation) throws Throwable;

}
