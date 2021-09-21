package cn.gyw.spring.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import cn.gyw.spring.service.DataService;

@Service(value = "databaseService")
public class DatabaseService implements DataService {
    @Override
    public void showDesc() {
        System.out.println("Database");
        privateShow();
        // 自调用，（最佳实践，不使用自调用）
//        showIntro(); // 不会被切面代理
        ((DataService)(AopContext.currentProxy())).showIntro();// 会被代理，需要添加expose-proxy="true"属性
    }

    @Override
    public void showIntro() {
        System.out.println("Database intro");
    }

    /**
     * 私有方法
     */
    private void privateShow() {
        System.out.println("this is private method show()");
    }
}
