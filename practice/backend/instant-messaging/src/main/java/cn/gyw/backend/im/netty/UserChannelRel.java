package cn.gyw.backend.im.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户id和channel的关联关系处理
 */
public class UserChannelRel {

    private static final Map<String, Channel> MANAGER = new ConcurrentHashMap<>();

    public static void save(String userId, Channel channel) {
        MANAGER.put(userId, channel);
    }

    public static Channel get(String userId) {
        return MANAGER.get(userId);
    }

    public static void output() {
        MANAGER.entrySet().forEach((entry) -> {
            System.out.println("userId:" + entry.getKey() + ", channelId:" + entry.getValue());
        });
    }
}
