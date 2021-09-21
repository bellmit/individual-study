package cn.gyw.spring.web;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Servlet 3.0+ 去 web.xml
 * 
 * 1. 需要 Servelt 容器
 */
public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 配置过滤器
	 * 
	 * @return
	 */
	@Override
	protected Filter[] getServletFilters() {
		// 请求编码
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(String.valueOf(StandardCharsets.UTF_8));
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}

	/**
	 * 自定义调节器Servlet
	 * 
	 * @param context
	 * @return
	 */
	@Override
	protected DispatcherServlet createDispatcherServlet(final WebApplicationContext context) {
		final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(context);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
}
