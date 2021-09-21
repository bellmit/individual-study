package cn.gyw.handwritten.gspring.web.servlet;

import java.util.List;

/**
 * Handler execution chain, consisting of handler object and any handler interceptors.
 * 
 * 处理器执行链对象，组合处理器和任意拦截器
 */
public class GHandlerExecutionChain {

	private final Object handler;
	
	private GHandlerInterceptor[] interceptors;
	
	private List<GHandlerInterceptor> interceptorList;
	
	public GHandlerExecutionChain(Object handler, GHandlerInterceptor... interceptors) {
		this.handler = handler;
		this.interceptors = interceptors;
	}
	
	/**
	 * Return the handler object to execute.
	 */
	public Object getHandler() {
		return this.handler;
	}
}
