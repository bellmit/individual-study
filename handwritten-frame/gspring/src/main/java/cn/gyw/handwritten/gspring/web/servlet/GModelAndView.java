package cn.gyw.handwritten.gspring.web.servlet;

import java.util.Map;

public class GModelAndView {

	private String viewName;
	private Map<String, ?> model;

	public GModelAndView() {
	}

	public GModelAndView(String viewName) {
		super();
		this.viewName = viewName;
	}

	public GModelAndView(String viewName, Map<String, ?> model) {
		super();
		this.viewName = viewName;
		this.model = model;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Map<String, ?> getModel() {
		return model;
	}

	public void setModel(Map<String, ?> model) {
		this.model = model;
	}

}
