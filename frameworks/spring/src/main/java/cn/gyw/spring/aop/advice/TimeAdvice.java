package cn.gyw.spring.aop.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by guanyw on 2018/11/8.
 */
public class TimeAdvice implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("【后置通知】：" + method.getName() + "; 返回值：" + returnValue);
	}

}
