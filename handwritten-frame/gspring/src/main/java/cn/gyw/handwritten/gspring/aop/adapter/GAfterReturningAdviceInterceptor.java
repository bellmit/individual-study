package cn.gyw.handwritten.gspring.aop.adapter;

import cn.gyw.handwritten.gspring.aop.GAfterAdvice;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInterceptor;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInvocation;

import java.lang.reflect.Method;

/**
 * 后置通知包装器
 * 
 * 将后置通知包装为方法拦截器（GMethodInterceptor）
 */
public class GAfterReturningAdviceInterceptor extends GAbstractAspectAdvice
		implements GAfterAdvice, GMethodInterceptor {

	private GJoinPoint joinPoint;

	public GAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
		super(aspectMethod, aspectTarget);
	}

	@Override
	public Object invoke(GMethodInvocation mi) throws Throwable {
		Object returnValue = mi.proceed();
		this.joinPoint = mi;
		this.afterReturning(returnValue, mi.getMethod(), mi.getArguments(), mi.getThis());
		return returnValue;
	}

	private void afterReturning(Object returnValue, Method method, Object[] arguments, Object aThis) throws Exception {
		super.invokeAdviceMethod(this.joinPoint, returnValue, null);
	}
}
