package cn.gyw.springboot.webmvc.async;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 异步接口Demo
 *
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.FutureTask 支持取消行为的异步任务执行器
 * @see java.util.concurrent.ExecutorService
 */
@RestController
@RequestMapping("/asyncDemo")
public class AsyncController {

    @Resource
    private UserService userService;

    @Resource
    private MyFutureTask myFutureTask;

    @GetMapping("/index")
    public String index() {
        return "启动用户模块成功~~~~~~~~";
    }

    //http://localhost:8080/api/user/get/data?userId=4

    @GetMapping("/get/data")
    public UserBehaviorDataDTO getUserData(Long userId) {
        System.out.println("UserController的线程:" + Thread.currentThread());
        long begin = System.currentTimeMillis();
        UserBehaviorDataDTO userAggregatedResult = myFutureTask.getUserAggregatedResult(userId);
        long end = System.currentTimeMillis();
        System.out.println("===============总耗时:" + (end - begin) / 1000.0000 + "秒");
        return userAggregatedResult;
    }
}
