package cn.gyw.backend.im.config;

import java.io.Serializable;

import cn.gyw.backend.im.handler.MessageHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.gyw.backend.im.enums.TopicChannels;
import cn.gyw.backend.im.subscriber.ImMsgTopicSubscriber;
import cn.gyw.backend.im.subscriber.TestTopicSubscriber;

/**
 * 监听者配置
 */
@Configuration
@ConditionalOnProperty(name = "cache.provider", havingValue = "1")
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> serialRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        //设置key的存储方式为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置为value的存储方式为JDK二进制序列化方式，还有jackson序列化方式（Jackson2JsonRedisSerialize）
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        //注入Lettuce连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate(lettuceConnectionFactory);
        return redisTemplate;
    }

    /**
     * 定义监听器容器
     * 若无定义，发布订阅无效
     * @param redisConnectionFactory
     * @param testListenerAdapter
     * @param imMessageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       MessageListenerAdapter testListenerAdapter,
                                                                       MessageListenerAdapter imMessageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(testListenerAdapter, new PatternTopic(TopicChannels.TEST.getChannelName()));
        container.addMessageListener(imMessageListenerAdapter,
                new PatternTopic(TopicChannels.IM_MESSAGE_CHANNEL.getChannelName()));
        return container;
    }
    
    // 订阅者
    @Bean
    public MessageListenerAdapter testListenerAdapter() {
        TestTopicSubscriber testTopicSubscriber = new TestTopicSubscriber();
        return new MessageListenerAdapter(testTopicSubscriber, "onMessage");
    }
    
    // 订阅者
    @Bean
    public MessageListenerAdapter imMessageListenerAdapter(RedisTemplate<String, String> stringRedisTemplate) {
        ImMsgTopicSubscriber imMessageTopicSubscriber = new ImMsgTopicSubscriber(stringRedisTemplate);
        return new MessageListenerAdapter(imMessageTopicSubscriber, "onMessage");
    }
}
