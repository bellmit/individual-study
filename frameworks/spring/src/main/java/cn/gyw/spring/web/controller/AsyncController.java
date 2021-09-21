package cn.gyw.spring.web.controller;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import cn.gyw.spring.web.DeferredResultQueue;

@Controller
public class AsyncController {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncController.class);

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> createOrder() {
        DeferredResult<Object> deferredResult = new DeferredResult<>(5000L, "create fail");
        DeferredResultQueue.save(deferredResult);

        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create() {
        String orderId = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(orderId);

        return "success : " + orderId;
    }

    /**
     * preHandle.../springmvc-annotation/async01
     * 		主线程开始...Thread[http-bio-8081-exec-3,5,main]==>1513932494700
     * 		主线程结束...Thread[http-bio-8081-exec-3,5,main]==>1513932494700
     * 		=========DispatcherServlet及所有的Filter退出线程============================
     *
     * 		================等待Callable执行==========
     * 		副线程开始...Thread[MvcAsync1,5,main]==>1513932494707
     * 		副线程开始...Thread[MvcAsync1,5,main]==>1513932496708
     * 		================Callable执行完成==========
     *
     * 		================再次收到之前重发过来的请求========
     * 		preHandle.../springmvc-annotation/async01
     * 		postHandle...（Callable的之前的返回值就是目标方法的返回值）
     * 		afterCompletion...
     *
     * 		异步的拦截器:
     * 			1）、原生API的AsyncListener
     * 			2）、SpringMVC：实现AsyncHandlerInterceptor；
     * @return
     */
    @ResponseBody
    @RequestMapping("/async01")
    public Callable<String> async01() {
        LOG.info("主线程[{}]开始=>StartTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());

        Callable<String> result = new Callable<String>() {

            @Override
            public String call() {
                LOG.info("副线程[{}]开始=>StartTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                }
                LOG.info("副线程[{}]结束=>EndTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());
                return "Callable<String> async01()";
            }
        };

        LOG.info("主线程[{}]结束=>EndTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());
        return result;
    }
}
