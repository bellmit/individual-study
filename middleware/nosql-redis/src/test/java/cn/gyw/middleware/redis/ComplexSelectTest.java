package cn.gyw.middleware.redis;

import com.alibaba.fastjson.JSONObject;

import cn.gyw.middleware.redis.model.User;

import org.junit.Test;

import java.util.*;

/**
 * Redis 实现类似 where 条件的复杂查询
 *
 * 提示：
 * 1）使用多种数据结构组合查询
 *
 */
public class ComplexSelectTest extends AbstractTest {

    private static final String USER_KEY = "T_USER";
    private static final String SEX_PREFIX = "USER_SEX_";
    private static final String AGE_PREFIX = "USER_AGE_";

    /**
     * select * from T_USER where age = 24
     * select * from T_USER where age = 24 and sex = "man"
     * select * from T_USER where age = 24 or sex = "man"
     *
     */
    @Test
    public void whereCondition() {
        // age = 24
        Set<String> idList = jedis.smembers(AGE_PREFIX + 24);
        for (String uid : idList) {
            System.out.println(jedis.hget(USER_KEY, uid));
        }

        // age = 24 and sex = woman 取交集
        System.out.println("age=24 and sex=woman >>");
        idList = jedis.sinter(AGE_PREFIX + 24, SEX_PREFIX + "woman");
        for (String uid : idList) {
            System.out.println(jedis.hget(USER_KEY, uid));
        }

        //age =24 or sex = man 取并集
        System.out.println("age=24 or sex=man >>");
        idList = jedis.sunion(AGE_PREFIX + 24, SEX_PREFIX + "man");
        for (String uid : idList) {
            System.out.println(jedis.hget(USER_KEY, uid));
        }
    }

    /**
     * 模拟大量用户数据存储在缓存中
     */
    @Test
    public void initUserDatas() {
        // 删除所有数据
        jedis.flushAll();

        String[] userNames = {"zhang3", "wang5", "li4", "guanyw"};
        String[] sex = {"man", "woman"};

        Map<String, String> datas = new HashMap<>();
        User user = null;
        String uuid = null;
        Random random = new Random();
        String age = null;
        String randSex = null;
        // 做放入操作
        for (int i = 0 ; i < 10 ; ++ i) {
            uuid = UUID.randomUUID().toString();
            age = String.valueOf(random.nextInt(100));
            randSex = sex[i % 2];
            user = new User(uuid,
                    userNames[i % 4] + i,
                    age, randSex);
            datas.put(uuid, JSONObject.toJSONString(user));
            // 维护age 的set集合
            jedis.sadd(AGE_PREFIX + age, uuid);
            // 维护sex 的set集合
            jedis.sadd(SEX_PREFIX + randSex, uuid);
        }

        jedis.hmset(USER_KEY, datas);
    }
}
