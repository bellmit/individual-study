package cn.gyw.spring.web.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于AbstractController
 */
@Component(value = "/abs")
public class ControllerByAbstractController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(this.getClass().getName());
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
