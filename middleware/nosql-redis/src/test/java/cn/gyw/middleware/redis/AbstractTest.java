package cn.gyw.middleware.redis;

import org.junit.After;
import org.junit.Before;

import cn.gyw.middleware.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 抽象测试类
 */
public abstract class AbstractTest {

    protected JedisUtil jedisUtil = JedisUtil.getInstance();
    protected Jedis jedis;

    /**
     * 获取Redis 连接
     */
    @Before
    public void beforeMethod() {
        jedis = jedisUtil.getJedis();
    }

    /**
     * 释放资源
     */
    @After
    public void afterMethod() {
        jedis.close();
        System.out.println("========success=======");
    }
}
