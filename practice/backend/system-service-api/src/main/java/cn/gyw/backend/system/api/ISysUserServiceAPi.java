package cn.gyw.backend.system.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ISysUserServiceAPi {
    
    // feign使用必须要加@RequestParam，否则会被当成Post
    // 直接返回boolean 类型会直接报错，原因待分析
    @GetMapping(path = "/check") 
    String checkUser(@RequestParam(value = "userId", required = false) String userId);
}
