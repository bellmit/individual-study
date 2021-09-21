package cn.gyw.handwritten.gspring.aop.adapter;

import cn.gyw.handwritten.gspring.aop.GBeforeAdvice;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInterceptor;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInvocation;

import java.lang.reflect.Method;

/**
 * 前置通知包装器
 * 
 * 将前置通知包装为方法拦截器（GMethodInterceptor）
 */
public class GMethodBeforeAdviceInterceptor extends GAbstractAspectAdvice implements GBeforeAdvice, GMethodInterceptor {

	private GJoinPoint joinPoint;

	public GMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
		super(aspectMethod, aspectTarget);
	}

	private void before(Method method, Object[] args, Object target) throws Throwable {
		super.invokeAdviceMethod(this.joinPoint, null, null);
	}

	@Override
	public Object invoke(GMethodInvocation mi) throws Throwable {
		this.joinPoint = mi;
		// 从被织入的代码中才能拿到，JoinPoint
		before(mi.getMethod(), mi.getArguments(), mi.getThis());
		return mi.proceed();
	}
}
