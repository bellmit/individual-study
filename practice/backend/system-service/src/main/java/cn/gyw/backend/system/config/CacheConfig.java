package cn.gyw.backend.system.config;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存配置类
 * 使用Lettuce客户端，自动注入配置的方式
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CacheConfig {

    /*@Value("${spring.redis.host}")
    private String host;

    //连接池配置信息（如果不用连接池可以省略这一步）
    @Bean
    //prefix，表示从配置文件读取以前缀开头的信息，注入到GenericObjectPoolConfig中
    @ConfigurationProperties(prefix="generic.pool.config")
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        return poolConfig;
    }

    //lettuce客户端配置信息（如果不用连接池通过LettuceClientConfiguration来builder）
    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration(GenericObjectPoolConfig genericObjectPoolConfig){
        //构造LettucePoolingClientConfiguration对象，同时加入连接池配置信息
        LettucePoolingClientConfiguration build = LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();
        return build;
    }

    //Redis集群配置信息
    @Bean
    @ConfigurationProperties(prefix="redis.cluster")
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        //配置集群信息（也可以从配置文件加载）
        ArrayList<RedisNode> list = new ArrayList<>();
        list.add(new RedisNode(host,6379));
//        list.add(new RedisNode(host,7002));
//        list.add(new RedisNode(host,7003));
//        list.add(new RedisNode(host,7004));
//        list.add(new RedisNode(host,7005));
//        list.add(new RedisNode(host,7006));
        redisClusterConfiguration.setClusterNodes(list);
        return redisClusterConfiguration;
    }

    //配置LettuceConnectionFactory连接工厂
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, LettuceClientConfiguration lettuceClientConfiguration){
        //通过配置RedisClusterConfiguration实例来创建工厂
        //构造该对象是传入Lettuce客户端配置和redis集群配置
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration,lettuceClientConfiguration);
        return lettuceConnectionFactory;
    }*/

    //配置RedisTemplate，默认注入 RedisTemplate<String, String> stringRedisTemplate
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
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisMessageTemplate = new RedisTemplate<>();
        // 默认的序列化
        redisMessageTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        redisMessageTemplate.setConnectionFactory(connectionFactory);
        return redisMessageTemplate;
    }

}
