package cn.gyw.handwritten.gspring.aop.framework;

import cn.gyw.handwritten.gspring.aop.intercept.GMethodInterceptor;
import cn.gyw.handwritten.gspring.aop.intercept.GMethodInvocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GReflectiveMethodInvocation implements GMethodInvocation {

	private Object proxy;
	private Object target;
	private Method method;
	private Object[] arguments;
	private Class<?> targetClass;
	private List<Object> interceptorsAndDynamicMethodMatchers;

	// 定义一个索引，从-1开始记录当前拦截器执行的位置
	private int currentInterceptorIndex = -1;

	private Map<String, Object> extMap;

	protected GReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
			Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

		this.proxy = proxy;
		this.target = target;
		this.targetClass = targetClass;
		this.method = method;
		this.arguments = arguments;
		this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
	}

	@Override
	public Object proceed() throws Throwable {
		// We start with an index of -1 and increment early.
		if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
			return this.method.invoke(this.target, this.arguments);
		}

		Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers
				.get(++this.currentInterceptorIndex);
		if (interceptorOrInterceptionAdvice instanceof GMethodInterceptor) {
			// Evaluate dynamic method matcher here: static part will already have
			// been evaluated and found to match.
			GMethodInterceptor gmi = (GMethodInterceptor) interceptorOrInterceptionAdvice;

			return gmi.invoke(this);
		} else {
			return proceed();
		}
	}

	@Override
	public Object getThis() {
		return this.target;
	}

	@Override
	public Object[] getArguments() {
		return this.arguments;
	}

	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public void setExt(String key, Object value) {
		if (value != null) {
			if (extMap == null) {
				extMap = new HashMap<>();
			}
			this.extMap.put(key, value);
		} else {
			if (this.extMap != null) {
				this.extMap.remove(key);
			}
		}
	}

	@Override
	public Object getExt(String key) {
		return this.extMap == null ? null : this.extMap.get(key);
	}
}
