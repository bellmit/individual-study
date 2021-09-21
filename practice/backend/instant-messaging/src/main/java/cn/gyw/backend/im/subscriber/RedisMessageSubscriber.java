package cn.gyw.backend.im.subscriber;


import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis 订阅者
 */
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] channel = message.getChannel();
        System.out.println("订阅：" + new String(channel));
        byte[] body = message.getBody();
    }
}
