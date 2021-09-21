package cn.gyw.backend.im;

import cn.gyw.backend.im.service.RedisBySpringCacheService;
import cn.gyw.backend.im.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redis 操作测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IMApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {CacheConfig.class})
@EnableCaching // 开启Spring-cache 否则无效
public class SpringCacheRedisTest {

    @Autowired
    private RedisBySpringCacheService redisService;

    @Test
    public void saveUser() {
        User user = new User("10013", "科比");
        redisService.saveUser(user);
//        redisService.saveUserByCondition("10012");
    }

    @Test
    public void cacheableByGet() {
        for (int i = 0 ; i < 3 ; i ++) {
            redisService.getUserById("3");
        }
    }
}
