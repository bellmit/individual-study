package cn.gyw.middleware.redis;

import org.junit.Test;

import java.util.List;

/**
 * Redis Hash 结构测试
 */
public class HashTest extends AbstractTest {

    private static final String HASH_TEST = "test_hash";

    /**
     * 新增 Hash 数据
     */
    @Test
    public void shouldAddOneData() {
        String user = "{\"id\":\"1003\",\"name\":\"gyw\"}";
        jedis.hset(HASH_TEST, "user_1003", user);
    }

    /**
     * 批量获取Hash
     */
    @Test
    public void shouldGetByList() {
        String[] userIds = new String[]{"user_1001", "user_1002", "user_1003"};
        List<String> datas = jedis.hmget(HASH_TEST, userIds);
        System.out.println(datas);
    }
}
