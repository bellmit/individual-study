package cn.gyw.spring.service.impl;

import org.springframework.stereotype.Service;

import cn.gyw.spring.service.DataService;

@Service(value = "cacheService")
public class CacheService implements DataService {
    @Override
    public void showDesc() {
        System.out.println("Cache");
    }

    @Override
    public void showIntro() {
        System.out.println("Cache intro");
    }
}
