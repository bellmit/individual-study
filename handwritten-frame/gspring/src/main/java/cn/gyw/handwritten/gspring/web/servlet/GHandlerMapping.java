package cn.gyw.handwritten.gspring.web.servlet;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * define a mapping between requests and handler objects
 * 
 * 请求和处理器映射
 */
public class GHandlerMapping {

	// 保存映射的方法
	private Method method;
	// 方法对应的实例
	private Object handler;
	// URL 正则表达式
	private Pattern pattern;

	public GHandlerMapping(Pattern pattern, Object handler, Method method) {
		super();
		this.pattern = pattern;
		this.handler = handler;
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

}
