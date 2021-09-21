package cn.gyw.handwritten.gspring.aop.adapter;

import cn.gyw.handwritten.gspring.aop.GThrowsAdvice;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInterceptor;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInvocation;

import java.lang.reflect.Method;

/**
 * 异常通知包装器
 * 
 * 将异常通知包装为方法拦截器（GMethodInterceptor）
 */
public class GAfterThrowingAdviceInterceptor extends GAbstractAspectAdvice
		implements GThrowsAdvice, GMethodInterceptor {

	private String throwName;

	public GAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
		super(aspectMethod, aspectTarget);
	}

	@Override
	public Object invoke(GMethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		} catch (Throwable e) {
			// TODO： 异常名称匹配
			invokeAdviceMethod(mi, null, e.getCause());
			throw e;
		}
	}

	public void setThrowName(String throwName) {
		this.throwName = throwName;
	}
}
