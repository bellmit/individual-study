package cn.gyw.spring.web.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于接口实现控制器
 */
@Component("/interface")
public class ControllerByInterface implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(this.getClass().getName());
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
