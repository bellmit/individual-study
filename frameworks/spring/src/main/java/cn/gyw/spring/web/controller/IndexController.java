package cn.gyw.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 页面重定向
     * @return
     */
    @RequestMapping(value = "/ping")
    public ModelAndView redirect() {

        return new ModelAndView("redirect:/index");
    }
}
