package cn.gyw.backend.im;

import java.io.Serializable;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gyw.backend.im.enums.TopicChannels;
import cn.gyw.backend.im.model.User;

/**
 * redis 操作测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {IMApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {CacheConfig.class})
public class RedisTemplateTest {

    // Object Serializable redis template
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Test
    public void setOperations() {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        setOperations.add("aaaaaa:1123", "1111");
    }

    @Test
    public void redisTemplate() {
        System.out.println(redisTemplate);
        //System.out.println(redisMessageTemplate);
    }

    // 发布
    @Test
    public void publicTopic() {
        String topic = TopicChannels.TEST.getChannelName();
//        redisTemplate.convertAndSend(topic, "1111111111");
//        redisMessageTemplate.convertAndSend(topic, "abcdefg");
    }

    @Test
    public void subscribe() {

    }

    @Test
    public void queryAllKeys() {
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach((str) -> {
            System.out.println("key >>" + str);
        });
    }

    @Test
    public void getStringByKey() {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        User user = (User) opsForValue.get("user:10001");
        System.out.println(user);
    }

    @Test
    public void setString() {
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();

        User user = new User();
        user.setIdCard("10001");
        user.setName("张三");

        opsForValue.set("user:10001", user);
    }

}
