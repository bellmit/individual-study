package cn.gyw.handwritten.gspring.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyw.handwritten.gspring.annotation.GController;
import cn.gyw.handwritten.gspring.annotation.GRequestMapping;
import cn.gyw.handwritten.gspring.context.GApplicationContext;

/**
 * MVC
 */
public class GDispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(GDispatcherServlet.class);

	private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

	/** List of HandlerMappings used by this servlet. */
	private List<GHandlerMapping> handlerMappings = new ArrayList<>();

	// 简化操作 TODO: Spring 适配其他语言
	private Map<GHandlerMapping, GHandlerAdapter> handlerAdapters;

	private List<GViewResolver> viewResolvers;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			this.doDispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("500 server error :" + Arrays.toString(e.getStackTrace()));
		}
	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 通过从request中拿到Url 匹配 handler
		GHandlerMapping handlerMapping = getHandlerMapping(request);
		if (handlerMapping == null) {
			processDispatchResult(request, response, new GModelAndView("404"));
			return;
		}
		// 2. 准备调用前参数
		GHandlerAdapter ha = getHandlerAdapter(handlerMapping);

		LOG.info("ha :{}", ha);
		// 3. 真正调用方法
		GModelAndView mv = ha.handle(request, response, handlerMapping);

		// 4. 视图处理，真正的响应输出
		processDispatchResult(request, response, mv);
	}

	/**
	 * 把ModelAndView转换为HTML、OutputStream、json、freemarker等
	 * 
	 * @param request
	 * @param response
	 * @param mv
	 * @throws Exception
	 */
	private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, GModelAndView mv)
			throws Exception {
		if (mv.equals(null)) {
			return;
		}

		if (this.viewResolvers.isEmpty()) {
			return;
		}

		for (int i = 0, len = this.viewResolvers.size(); i < len; i++) {
			GView view = this.viewResolvers.get(i).resolveViewName(mv.getViewName(), Locale.CHINA);
			view.render(mv.getModel(), request, response);
			// 只有1个viewResolver
			return ;
		}
	}

	private GHandlerAdapter getHandlerAdapter(GHandlerMapping handlerMapping) {
		if (this.handlerAdapters == null) {
			return null;
		}
        GHandlerAdapter ha = this.handlerAdapters.get(handlerMapping);
        if (ha.supports(handlerMapping)) {
            return ha;
        }
		return null;
	}

	/**
	 * 获取到请求路径 -- method 的映射
	 * 
	 * @param request
	 * @return
	 */
	private GHandlerMapping getHandlerMapping(HttpServletRequest request) {
		// url全路径 /rootPath/{apiPath}
		String urlPath = request.getRequestURI();
		// 项目根路径
		String contentPath = request.getContextPath();
		urlPath = urlPath.replace(contentPath, "").replaceAll("/+", "/");
		for (GHandlerMapping hm : this.handlerMappings) {
			if (hm.getPattern().matcher(urlPath).matches()) {
				// 若匹配，直接返回
				return hm;
			}
		}
		return null;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 1. ApplicationContext 初始化
		GApplicationContext context = new GApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));

		// 2. MVC 6大组件初始化
		initStrategies(context);
	}

	// 初始化策略
	protected void initStrategies(GApplicationContext context) {
		// 多文件上传的组件
		initMultipartResolver(context);
		// 初始化本地语言环境
		initLocaleResolver(context);
		// 初始化主题解析器
		initThemeResolver(context);

		// 初始化HandlerMapping，必须实现
		initHandlerMappings(context);
		// 初始化Handler适配器，必须实现
		initHandlerAdapters(context);

		// 初始化异常拦截器
		initHandlerExceptionResolvers(context);
		// 初始化视图预处理器
		initRequestToViewNameTranslator(context);
		// 初始化试图转换器，必须实现
		initViewResolvers(context);

		// 初始化闪存管理器
		initFlashMapManager(context);
	}

	private void initFlashMapManager(GApplicationContext context) {
	}

	private void initViewResolvers(GApplicationContext context) {
		this.viewResolvers = new ArrayList<>();
		// 拿到模板的存放目录
		String templateRoot = context.getConfig().getProperty("templateRoot");
		URL url = this.getClass().getClassLoader().getResource(templateRoot);

		try {
			Path templateRootPath = Paths.get(url.toURI());
			Files.walk(templateRootPath).forEach(path -> {
			    // 兼容多模块的目的，实际上只需要一个ViewResolver就可以
                // 为了仿真，用list
				this.viewResolvers.add(new GViewResolver(templateRootPath));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initRequestToViewNameTranslator(GApplicationContext context) {
	}

	private void initHandlerExceptionResolvers(GApplicationContext context) {
	}

	private void initHandlerAdapters(GApplicationContext context) {
		this.handlerAdapters = new HashMap<GHandlerMapping, GHandlerAdapter>();
		// 把一个request请求变成handler，参数是字符串，自动配到handler中的形参
		for (GHandlerMapping handlerMapping : this.handlerMappings) {
			this.handlerAdapters.put(handlerMapping, new GHandlerAdapter());
		}
	}

	private void initHandlerMappings(GApplicationContext context) {
		String[] beanNames = context.getBeanDefinitionNames();
		try {
			for (int i = 0, len = beanNames.length; i < len; i++) {
				Object instance = context.getBean(beanNames[i]);
				Class<?> clazz = instance.getClass();
				if (!clazz.isAnnotationPresent(GController.class)) {
					continue;
				}
				String baseUrl = clazz.getAnnotation(GRequestMapping.class).value();

				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					if (!method.isAnnotationPresent(GRequestMapping.class)) {
						continue;
					}
					String regex = ("/" + baseUrl + "/"
							+ method.getAnnotation(GRequestMapping.class).value().replaceAll("\\*", ".*")).replaceAll("/+",
									"/");
					// TODO: 需要去重
					this.handlerMappings.add(new GHandlerMapping(Pattern.compile(regex), instance, method));
					LOG.info("initHandlerMappings {},{},{}", regex, instance, method);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initThemeResolver(GApplicationContext context) {
	}

	private void initLocaleResolver(GApplicationContext context) {
	}

	private void initMultipartResolver(GApplicationContext context) {
	}
}
