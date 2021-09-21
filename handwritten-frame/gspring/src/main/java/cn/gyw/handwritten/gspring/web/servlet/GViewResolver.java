package cn.gyw.handwritten.gspring.web.servlet;

import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public class GViewResolver {
	
	private final String DEFAULT_TEMPLATE_SUFFIX = ".html";
	
	private File templateRootDir;
	
	public GViewResolver(Path templateRoot) {
		this.templateRootDir = templateRoot.toFile();
	}
	
	GView resolveViewName(String viewName, Locale locale) throws Exception {
		if (viewName == null || "".equals(viewName.trim())) {
			return null;
		}
		viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : viewName + DEFAULT_TEMPLATE_SUFFIX;
		File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
		return new GView(templateFile);
	}

}
