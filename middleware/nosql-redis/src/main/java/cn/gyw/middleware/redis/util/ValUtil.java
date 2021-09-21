package cn.gyw.middleware.redis.util;

import java.time.LocalDateTime;

/**
 * 值工具类
 *
 * 存入Zset 的value 值
 */
public class ValUtil {

    private static Long KEY= 10000000000L;

    /**
     * 将coin加密成可以存在zset的值，实际上就是 coin * 10000000 + now % 10000000
     * @param coin
     * @return
     */
    public static Double encrypt(Long coin){
        long currentMills = System.currentTimeMillis();
        System.out.println(currentMills);
        Long value = coin * KEY + (KEY - currentMills % KEY);
        return value.doubleValue();
    }

    /**
     * 将zset的值转成long型的coin
     * @param value
     * @return
     */
    public static Long decrypt(Double value){
        Double coin = value / KEY;
        return coin.longValue();
    }
}
