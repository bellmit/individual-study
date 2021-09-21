package cn.gyw.middleware.redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * redis cluster 集群 java 连接
 */
public class RedisClusterTest {

    /**
     * 连接 集群
     */
    @Test
    public void shouldConnect() {
        Set<HostAndPort> jedisConfigNode = new HashSet<>();
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7001));
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7002));
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7003));
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7004));
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7005));
        jedisConfigNode.add(new HostAndPort("192.168.1.181", 7006));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(20);
        config.setMaxWaitMillis(-1);
        config.setTestOnBorrow(true);
        // jedis 2.7 新增的cluster类
        JedisCluster jedisCluster = new JedisCluster(jedisConfigNode, 6000, 100, config);

        System.out.println(jedisCluster.get("name"));
    }
}
