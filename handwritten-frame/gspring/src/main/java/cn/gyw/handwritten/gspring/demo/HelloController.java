package cn.gyw.handwritten.gspring.demo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gyw.handwritten.gspring.annotation.GAutowired;
import cn.gyw.handwritten.gspring.demo.service.IHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyw.handwritten.gspring.annotation.GController;
import cn.gyw.handwritten.gspring.annotation.GRequestMapping;
import cn.gyw.handwritten.gspring.annotation.GRequestParam;
import cn.gyw.handwritten.gspring.web.servlet.GModelAndView;

@GController
@GRequestMapping("/hello")
public class HelloController {

	private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

	@GAutowired
	private IHelloService helloService;
	
	@GRequestMapping("/json")
	public void queryJson(@GRequestParam String name, HttpServletResponse response) throws IOException {
		String str = "{\"name\":\"" + name + "\", \"now\":" + LocalDateTime.now() + "}";
		response.getWriter().write(str);
	}
	
	@GRequestMapping("/say*")
	public GModelAndView sayHello(HttpServletRequest request, HttpServletResponse response,
			@GRequestParam("name") String name) {
		LOG.debug("say hello api , name :{}", name);
		String result = this.helloService.sayHello(name);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("content", result);
		GModelAndView mv = new GModelAndView();
		mv.setModel(model);
		mv.setViewName("demo");
		return mv;
	}
	
	@GRequestMapping("/ex")
	public GModelAndView exception(HttpServletRequest request, HttpServletResponse response,
			@GRequestParam("name") String name, @GRequestParam("addr") String addr) {
		LOG.debug("Request name :{}, addr :{}", name, addr);
		try {
			this.helloService.mockException();
			return new GModelAndView("demo");
		} catch (Exception e) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("detail", e.getMessage());
			model.put("stackTrace", Arrays.toString(e.getStackTrace()));
			return new GModelAndView("500", model);
		}
	}
}
