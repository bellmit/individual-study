package cn.gyw.spring.service.user2;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

// @Component(value = "userService")
// @Conditional()
public class UserService {

    public void info() {
        System.out.println("UserService");
    }

}
