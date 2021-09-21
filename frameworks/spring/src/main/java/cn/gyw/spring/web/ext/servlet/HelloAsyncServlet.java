package cn.gyw.spring.web.ext.servlet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步请求 Servlet
 * <p>
 * asyncSupported = true : 开启异步支持
 */
@WebServlet(urlPatterns = {"/hello-async"}, asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloAsyncServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、支持异步处理asyncSupported=true
        //2、开启异步模式
        LOG.info("主线程[{}]开始=>StartTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());
        AsyncContext startAsync = req.startAsync();

        //3、业务逻辑进行异步处理;开始异步处理
        startAsync.start(() -> {
            try {
                LOG.info("副线程[{}]开始，开始时间：{}", Thread.currentThread(), System.currentTimeMillis());
                sayHello();

                //获取到异步上下文
                AsyncContext asyncContext = req.getAsyncContext();
                //4、获取响应
                ServletResponse response = asyncContext.getResponse();
                resp.getWriter().write("hello async....");

                // 标志完成
                startAsync.complete();

                LOG.info("副线程[{}]结束，结束时间：{}", Thread.currentThread(), System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        LOG.info("主线程[{}]结束=>EndTime :{}", Thread.currentThread().getName(), System.currentTimeMillis());
    }

    private void sayHello() throws Exception {
        LOG.info("线程[{}] is processing...", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(3);
    }
}
