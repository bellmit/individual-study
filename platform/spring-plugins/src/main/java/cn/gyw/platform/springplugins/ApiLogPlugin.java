package cn.gyw.platform.springplugins;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.aop.MethodBeforeAdvice;

public class ApiLogPlugin implements MethodBeforeAdvice {

	private AtomicInteger counter = new AtomicInteger(0);
	
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("Api request, count :" + counter.getAndIncrement());
	}
}
