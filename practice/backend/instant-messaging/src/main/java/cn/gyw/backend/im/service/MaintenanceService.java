package cn.gyw.backend.im.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static cn.gyw.backend.im.constants.ImKeys.*;

/**
 * 消息维护
 */
@Component
public class MaintenanceService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 更新用户在线状态
     */
    public void updateLoginUserState() {
        LOG.debug("开始更新用户在线状态...");
        Set<String> tokens = redisTemplate.opsForSet().members(USER_TOKEN_SET);
        // TODO: 获取所有token 判断是否在线，如果不在线删除
        for (String token : tokens) {
            if (!isOnline(token)) {
                redisTemplate.opsForSet().remove(USER_TOKEN_SET, token);
                String tokenKey = USER_TOKEN + token;
                String username = redisTemplate.opsForValue().get(tokenKey);
                if (username != null) {
                    redisTemplate.opsForSet().remove(USER_ONLINE_LIST, username);
                    redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(tokenKey.getBytes()));
                }
            }
        }
        LOG.debug("更新用户在线状态结束。");
    }

    /**
     * 用户是否在线
     *
     * @param token
     * @return
     */
    private boolean isOnline(String token) {
        return redisTemplate.hasKey(USER_TOKEN + token);
    }

    public void maintenanceHisMessage() {
        LOG.debug("开始维护redis队列...");
        maintanceUserMessageHis();
        maintanceOfflineMessageHis();
        LOG.debug("维护队列结束。");
    }

    /**
     * 实现：从队列末尾（left）依次循环
     */
    private void maintanceUserMessageHis() {
        LOG.debug("1-开始维护用户历史消息队列...");
        Set<String> keys = redisTemplate.keys(SESSION_MESSAGE_HIS + "*");
        checkMessageList(keys);
        LOG.debug("1-维护用户历史消息队列结束。");
    }

    /**
     * 实现：从队列末尾（left）依次循环
     */
    private void maintanceOfflineMessageHis() {
        LOG.debug("2-开始维护用户离线消息队列...");
        Set<String> keys = redisTemplate.keys(SESSION_OFFLINE_MESSAGE + "*");
        checkMessageList(keys);
        LOG.debug("2-维护用户离线消息队列结束。");
    }

    private void checkMessageList(Set<String> keys) {
        ListOperations<String, String> listOperation = redisTemplate.opsForList();
        for (String key : keys) {
            LOG.debug("key is {}", key);
            if (Objects.isNull(key)) {
                return;
            }
            long size = listOperation.size(key);
            if (Objects.isNull(size) || size < 0L) {
                return;
            }

            String messageId;
            while (size > 0) {
                messageId = listOperation.leftPop(key);
                if (redisTemplate.hasKey(messageId)) { // 消息未过期，重新放入，结束循环
                    listOperation.leftPush(key, messageId);
                    break;
                }
                size--;
            }
        }
    }
}
