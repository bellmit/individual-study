package cn.gyw.spring.aop.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 实现具体的增强类接口
 * Created by guanyw on 2018/11/6.
 */
public class LogAdvice implements MethodBeforeAdvice{
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("【前置通知】：" + method.getName() + " : " + target);
	}
}
