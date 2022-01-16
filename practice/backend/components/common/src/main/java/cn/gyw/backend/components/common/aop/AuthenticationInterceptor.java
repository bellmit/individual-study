package cn.gyw.backend.components.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gyw.backend.components.common.annotations.PassToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Token 验证拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	private Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("X-Token");
		log.debug("intercept handler :{}, token:{}", handler.getClass(), token);
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		
		if (method.isAnnotationPresent(PassToken.class)) { // 跳过认证
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		
		Class<?> handlerClass = handlerMethod.getBeanType().getClass();
		log.debug("handler class is :{}", handlerClass);
		
		return true;
	}
}
