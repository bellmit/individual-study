package cn.gyw.middleware.redis;

import org.junit.Test;

import cn.gyw.middleware.redis.util.ValUtil;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 通过Redis实现 排行榜功能
 *
 * 基于：sortedSet
 */
public class RankByRedisTest extends AbstractTest {

    private final String rankName = "user_rank";
    private final String valPrix = "user_";

    /**
     * 新增或更新用户信息
     *
     * 用户数据结构
     * id|uid|coin|create_time|update_time
     * 1|1000|24|2019-4-1|2019-5-1
     * 2|1001|54|2019-3-1|2019-5-1
     * 3|1002|54|2019-3-1|2019-7-1
     */
    @Test
    public void addOrUpdateUserInfo() {
        // 新增用户
        Map<String, Double> userScore = new HashMap<>();
        for (int i = 1000 ; i < 1021 ; i ++) {
            userScore.put(valPrix + i, ValUtil.encrypt((long) new Random().nextInt()));
        }

        jedis.zadd(rankName, userScore);
        // 更新用户
//        jedis.zadd(rankName, 12d, valPrix + uid);
    }

    /**
     * 实现排行榜功能
     *
     */
    @Test
    public void shouldGetZsetInfo() {
        // 获取记录数
        long total = jedis.zcard(rankName);
        System.out.println("记录总数：" + total);

        // 获取指定用户分数
        Double score = jedis.zscore(rankName, valPrix + "1000");
        System.out.println(score);

        // 获取用户的排名
        Long rank = jedis.zrank(rankName, valPrix + "1002");
        System.out.println("从小到大排序：" + (rank + 1));
        rank = jedis.zrevrank(rankName, valPrix + "1002");
        System.out.println("从大到小排序：" + (rank + 1));

        // 获取某个范围内的用户排名(从小到大)
        Set<Tuple> datas = jedis.zrangeWithScores(rankName, 0 , 100);
        // 从大到小
        datas = jedis.zrevrangeWithScores(rankName, 0, 100);
        printTuple(datas);
    }

    /**
     * 分页查询
     */
    @Test
    public void hashOperate() {
        // zrevrangeByScoreWithScores
        Set<Tuple> datas = jedis.zrevrangeByScoreWithScores(rankName, "82882871870193", "0", 0, 10);
        printTuple(datas);

        int page = 1;
        int limit = 10;
        // zrevrange
        Set<String> userNameList = jedis.zrevrange(rankName, (page - 1) * limit, page * limit - 1);
        System.out.println(userNameList);
    }

    /**
     * 打印Tuple 数据
     * @param tuples
     */
    private void printTuple(final Set<Tuple> tuples) {
        for (Tuple data : tuples) {
            System.out.println(data.getElement() + " : " + data.getScore());
        }
    }

}
