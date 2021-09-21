package cn.gyw.handwritten.gspring.aop.framework;

import cn.gyw.handwritten.gspring.aop.adapter.GAfterReturningAdviceInterceptor;
import cn.gyw.handwritten.gspring.aop.adapter.GAfterThrowingAdviceInterceptor;
import cn.gyw.handwritten.gspring.aop.adapter.GMethodBeforeAdviceInterceptor;
import cn.gyw.handwritten.gspring.aop.config.GAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GAdvisedSupport {

	// 扫描类
	private Class<?> targetClass;

	private Object target;

	private GAopConfig config;

	// 切点匹配模式
	private Pattern pointCutClassPattern;

	/** Cache with Method as key and advisor chain List as value */
	private transient Map<Method, List<Object>> methodCache;

	public GAdvisedSupport(GAopConfig config) {
		this.config = config;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
		parse();
	}

	private void parse() {
		// 类匹配
		// public * cn.gyw.handwritten.gspring.demo.service..*Service..*(.*)
		String pointCut = config.getPointCut().replaceAll("\\.", "\\\\.").replaceAll("\\\\.\\*", ".*")
				.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		// public * cn.gyw.handwritten.gspring.demo.service..*Service
		String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
		// class cn.gyw.handwritten.gspring.demo.service..*Service
		pointCutClassPattern = Pattern
				.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

		try {
			this.methodCache = new HashMap<>();
			// 方法匹配
			Pattern pattern = Pattern.compile(pointCut);

			// 切面类
			Class<?> aspectClass = Class.forName(this.config.getAspectClass());
			Map<String, Method> aspectMethods = new HashMap<>();
			for (Method m : aspectClass.getMethods()) {
				aspectMethods.put(m.getName(), m);
			}

			for (Method m : this.targetClass.getMethods()) {
				String methodString = m.toString();
				if (methodString.contains("throws")) {
					methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
				}
				Matcher matcher = pattern.matcher(methodString);
				if (matcher.matches()) {
					// 执行器链
					List<Object> advices = new LinkedList<>();
					// 把每一个方法包装成MethodInterceptor
					// before
					if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore().trim()))) {
						// 创建一个通知对象
						advices.add(new GMethodBeforeAdviceInterceptor(aspectMethods.get(this.config.getAspectBefore()),
								aspectClass.newInstance()));
					}
					// after
					if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter().trim()))) {
						// 创建一个通知对象
						advices.add(new GAfterReturningAdviceInterceptor(
								aspectMethods.get(this.config.getAspectAfter()), aspectClass.newInstance()));
					}

					// afterThrowing
					if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore().trim()))) {
						// 创建一个通知对象
						GAfterThrowingAdviceInterceptor afterThrowingAdvice = new GAfterThrowingAdviceInterceptor(
								aspectMethods.get(this.config.getAspectAfterThrow()), aspectClass.newInstance());
						afterThrowingAdvice.setThrowName(this.config.getAspectThrowExceptionName());
						advices.add(afterThrowingAdvice);
					}
					methodCache.put(m, advices);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public boolean pointCutMatch() {
		return this.pointCutClassPattern.matcher(this.targetClass.toString()).matches();
	}

	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass)
			throws NoSuchMethodException {
		List<Object> cached = this.methodCache.get(method);
		if (cached == null) {
			Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());

			// 底层逻辑，对代理方法进行兼容处理
			cached = methodCache.get(m);

			this.methodCache.put(m, cached);
		}
		return cached;
	}
}
