package cn.gyw.backend.im.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gyw.backend.im.model.User;

/**
 * Redis + Spring Cache 集成
 */
@Service
public class RedisBySpringCacheService {

    private final static Logger log = LoggerFactory.getLogger(RedisBySpringCacheService.class);

    @CachePut(value = "user", key = "#user.idCard", condition = "false")
    public void saveUser(User user) {
        log.info("保存了User:" + user.getIdCard());
    }

    @CacheEvict(value = "user", key = "#id", allEntries = false, beforeInvocation = false)
    public void deleteUser(String id) {
        log.info("删除user id：" + id);
    }

    @Cacheable(value = {"user1", "user2"}, key = "#id", condition = "false")
    public User saveUserByCondition(String id) {
        log.info("保存了User:" + id);
        return usersMap.get("2");
    }

    @Cacheable(value = "user", key = "#id")
    public User getUserById(String id) {
        log.info("getUserById " + id);
        return usersMap.get("4");
    }

    private static Map<String, User> usersMap = new HashMap<>();

    static {
        usersMap.put("1", new User("10002", "王二"));
        usersMap.put("2", new User("10003", "张三"));
        usersMap.put("3", new User("10004", "李四"));
        usersMap.put("4", new User("10005", "王五"));
    }
}
