package cn.gyw.spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 环绕增强
 * Created by guanyw on 2018/11/8.
 */
public class AroundAdvice implements MethodInterceptor {

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println(dateTimeFormat.format(new Date()));
		Object result = invocation.proceed();
		System.out.println(dateTimeFormat.format(new Date()));
		return result;
	}

}
