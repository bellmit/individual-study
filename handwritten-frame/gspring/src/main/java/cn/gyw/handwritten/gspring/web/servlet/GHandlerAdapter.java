package cn.gyw.handwritten.gspring.web.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gyw.handwritten.gspring.annotation.GRequestParam;

public class GHandlerAdapter {

	boolean supports(Object handler) {
		// TODO: 类型判断
		return (handler instanceof GHandlerMapping);
	}

	public GModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		GHandlerMapping handlerMapping = (GHandlerMapping) handler;

		// @RequestParam("name") String name, @RequestParam("value") String value
		// 方法的形参列表和request的参数列表顺序一一对应
		Map<String, Integer> paramIndexMapping = new HashMap<>();

		// 提取方法中加了注解的参数
		// 方法有多个参数，参数有多个注解
		Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
		// 形参数组
		Parameter[] parameters = handlerMapping.getMethod().getParameters();
		for (int i = 0, len = parameters.length; i < len; i++) {
			// 若存在注解，使用注解为 key
			for (Annotation annotation : pa[i]) {
				if (annotation instanceof GRequestParam) {
					GRequestParam mrp = (GRequestParam) annotation;
					if (!"".equals(mrp.value())) {
						paramIndexMapping.put(mrp.value(), i);
					} else {
						// From jdk8 support get parameter name
						// 若没有注解，使用形参名称
						paramIndexMapping.put(parameters[i].getName(), i);
					}
				}
			}
			// Request/Response 使用类型
			Class<?> paramType = parameters[i].getType();
			if (HttpServletRequest.class.isAssignableFrom(paramType)
					|| HttpServletResponse.class.isAssignableFrom(paramType)) {
				paramIndexMapping.put(paramType.getName(), i);
				continue;
			}
		}

		// 请求参数
		Map<String, String[]> requestParams = request.getParameterMap();
		// 实参数组
		Object[] paramValues = new Object[parameters.length];
		for (Map.Entry<String, String[]> paramEntry : requestParams.entrySet()) {
			if (!paramIndexMapping.containsKey(paramEntry.getKey())) {
				continue;
			}
			String value = Arrays.toString(paramEntry.getValue()).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
			int idx = paramIndexMapping.get(paramEntry.getKey());
			paramValues[idx] = convert(value, parameters[idx].getType());
		}
		if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
			int reqIdx = paramIndexMapping.get(HttpServletRequest.class.getName());
			paramValues[reqIdx] = request;
		}
		if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
			int respIdx = paramIndexMapping.get(HttpServletResponse.class.getName());
			paramValues[respIdx] = response;
		}
		
		// 方法执行
		Object returnValue = handlerMapping.getMethod().invoke(handlerMapping.getHandler(), paramValues);
		if (returnValue == null || returnValue instanceof Void) {
			return null;
		}
		// 若返回值是ModelAndView 直接返回
		if (handlerMapping.getMethod().getReturnType().equals(GModelAndView.class)) {
			return (GModelAndView) returnValue;
		}
		GModelAndView mv = new GModelAndView();
		// TODO
		return mv;
	}

	/**
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	private Object convert(String value, Class<?> type) {
		if (String.class.equals(type)) {
			return value.replaceAll("\\[|\\]", "").replaceAll("\\s", ",");
		}
		if (Integer.class.equals(type)) {
			return Integer.valueOf(value);
		}
		return value;
	};
}
