package cn.gyw.camel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * bean 由spring or camel 的beanPostProcessor 识别载入context
 */
@RestController
@RequestMapping("/hello")
class HelloController {

    HelloController() {
        println "helloController"
    }

    @GetMapping
    String hello() {
        return "hello"
    }
}
