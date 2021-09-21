package cn.gyw.backend.im.handler;

import static cn.gyw.backend.im.constants.ImKeys.IM_CONNECT_USER;
import static cn.gyw.backend.im.constants.ImKeys.SESSION_MESSAGE_HIS;
import static cn.gyw.backend.im.constants.ImKeys.SESSION_OFFLINE_MESSAGE;
import static cn.gyw.backend.im.constants.ImKeys.USER_INFO;
import static cn.gyw.backend.im.constants.ImKeys.USER_OFFLINE_MSG_CNT;
import static cn.gyw.backend.im.constants.ImKeys.USER_ONLINE_LIST;
import static cn.gyw.backend.im.constants.ImKeys.USER_TOKEN;
import static cn.gyw.backend.im.constants.ImKeys.USER_TOKEN_SET;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import com.alibaba.fastjson.JSONObject;

import cn.gyw.backend.im.enums.MessageTypeEnum;
import cn.gyw.backend.im.enums.TopicChannels;
import cn.gyw.backend.im.model.message.ImMessage;
import cn.gyw.backend.im.service.IMWebSocketService;
import cn.gyw.backend.im.util.ApplicationContextUtil;

/**
 * 消息处理中心
 */
public enum MessageHandler {

    INSTANCE;

    private final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    // session 容器
    private static final ConcurrentHashMap<String, IMWebSocketService> webSocketMap =
            new ConcurrentHashMap<>();

    private RedisTemplate<String, String> redisTemplate;

    /**
     * 处理实时消息，发送消息到redis channel 中
     *
     * @param imMessage 消息内容
     */
    public void handleMessage(ImMessage imMessage) {
        log.info("Handle message : {}", imMessage);
        String fromUserId = imMessage.getUserId();
        String toUserId = imMessage.getToUserId();
        String messageId = imMessage.getUid();
        // 目标sessionId
        String toSessionId = toUserId + ":" + fromUserId;
        imMessage.setToSessionId(toSessionId);

        String sessionId = fromUserId + ":" + toUserId;
        imMessage.setSessionId(sessionId);
        // 用户关系存储在 Set
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add(new String(IM_CONNECT_USER + fromUserId), toUserId);
        setOperations.add(new String(IM_CONNECT_USER + toUserId), fromUserId);
        // 消息关系存储在 List
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(SESSION_MESSAGE_HIS + sessionId, messageId);
        listOperations.leftPush(SESSION_MESSAGE_HIS + toSessionId, messageId);

        if (this.isOnlineSession(toSessionId)) { // 广播消息
            log.info("用户会话 [{}] 在线", toSessionId);
            imMessage.read();
            redisTemplate.opsForValue().set(messageId, JSONObject.toJSONString(imMessage));
            redisTemplate.expire(messageId, 30, TimeUnit.DAYS);
            redisTemplate.convertAndSend(TopicChannels.IM_MESSAGE_CHANNEL.getChannelName(), messageId);
        } else { // 离线消息
            log.info("用户会话 [{}] 离线，添加到离线消息列表", toSessionId);
            redisTemplate.opsForValue().set(messageId, JSONObject.toJSONString(imMessage));
            redisTemplate.expire(messageId, 30, TimeUnit.DAYS);
            // 增加离线消息
            this.addOfflineMessage(toSessionId, toUserId, messageId);
        }
    }

    /**
     * 获取两个用户之间最后一条消息，包括未读消息
     *
     * @param userId
     * @param fromUserId
     * @return
     */
    public ImMessage getLastMessage(String userId, String fromUserId) {
        String sessionId = getSessionId(fromUserId, userId);
        List<String> messageList = redisTemplate.opsForList().range(SESSION_MESSAGE_HIS + sessionId, 0L, 0L);
        if (messageList != null && messageList.size() > 0) {
            String messageId = messageList.get(0);
            String message = redisTemplate.opsForValue().get(messageId);
            return JSONObject.parseObject(message, ImMessage.class);
        }
        return null;
    }

    /**
     * 增加离线消息，离线消息数
     *
     * @param sessionId
     * @param userId
     * @param messageId
     */
    private void addOfflineMessage(String sessionId, String userId, String messageId) {
        String offlineMessageKey = SESSION_OFFLINE_MESSAGE + sessionId;
        String userOfflineMsgCntKey = USER_OFFLINE_MSG_CNT + userId;
        redisTemplate.opsForList().rightPush(offlineMessageKey, messageId);
        redisTemplate.opsForValue().increment(userOfflineMsgCntKey, 1L);
    }

    /**
     * 登录成功后，发送历史消息
     *
     * @param sessionId 会话唯一标识
     * @param userId    当前会话用户
     */
    public void loginAfter(String sessionId, String userId) {
        log.debug("login success, do after login ...");
        ListOperations<String, String> offlineListOps = redisTemplate.opsForList();
        long totalCnt = offlineListOps.size(SESSION_MESSAGE_HIS + sessionId); // 历史发送消息
        log.debug("用户[{}] 历史发送消息数：{}", userId, totalCnt);
        if (totalCnt == 0L) { // 无历史消息
            return;
        }
        long num = totalCnt;
        if (totalCnt > 50) {
            num = 50;
        }

        try {
            this.sendHisMessage(sessionId, 0, num); // 发送历史消息
        } catch (IOException e) {
            // TODO: 历史消息发送失败，应该提醒给客户端
            log.debug("发送历史消息失败...");
        }
        this.clearOfflineMessages(sessionId, userId); // 清空离线消息
    }

    private void sendHisMessage(String sessionId, long start, long end) throws IOException {
        IMWebSocketService socketService = webSocketMap.get(sessionId);
        if (Objects.isNull(socketService)) {
            log.debug("sessionId is null , no send his message");
            return;
        }

        ListOperations<String, String> offlineListOps = redisTemplate.opsForList();
        // 历史消息
        List<String> hisMessageList = offlineListOps.range(SESSION_MESSAGE_HIS + sessionId, start, end);
        Collections.reverse(hisMessageList); // 历史消息反转
        // 收到的离线消息
        List<String> offlineMessageList = offlineListOps.range(SESSION_OFFLINE_MESSAGE + sessionId, 0, -1);

        hisMessageList.stream().forEach((messageId) -> { // 用户历史消息包含已读和未读的消息
            String jsonMessage = redisTemplate.opsForValue().get(messageId);
            ImMessage imMessage = JSONObject.parseObject(jsonMessage, ImMessage.class);
            if (offlineMessageList.contains(messageId)) { // messageId 未读
                imMessage.read();
                redisTemplate.opsForValue().set(messageId, JSONObject.toJSONString(imMessage));
                this.sendReadInfo(sessionId, imMessage);
            }

            // 最终的消息需要从redis中取
            String rtMsg = redisTemplate.opsForValue().get(messageId);
            try {
                socketService.sendMessage(rtMsg); // 发送消息
            } catch (IOException e) {
                // TODO： 若消息发送失败了，离线消息也被清空了
                e.printStackTrace();
            }
        });

        // 发送完结消息
        ImMessage msg = new ImMessage();
        msg.setMessageType(MessageTypeEnum.HIS_MSG_OVER.code());
        msg.setContent("历史消息结束");
        msg.setCreatedMills(System.currentTimeMillis());
        socketService.sendMessage(msg);
    }

    /**
     * 获取离线消息列表
     *
     * @param userId
     * @param fromUserId
     * @param start
     * @param end
     * @return
     */
    public List<ImMessage> getHisMessages(String userId, String fromUserId, int start, int end) {
        String sessionId = this.getSessionId(fromUserId, userId);
        List<String> msgIds = redisTemplate.opsForList().range(SESSION_MESSAGE_HIS + sessionId, start, end);
        List<ImMessage> result = new ArrayList<>(msgIds.size());
        msgIds.forEach((id) -> {
            String message = redisTemplate.opsForValue().get(id);
            ImMessage imMessage = JSONObject.parseObject(message, ImMessage.class);
            if (!imMessage.isRead()) { // 消息未读
                imMessage.read();
                redisTemplate.opsForValue().set(id, JSONObject.toJSONString(imMessage));
            }

            result.add(imMessage);
        });

        return result;
    }

    /**
     * @param sessionId
     * @param message
     * @return
     */
    private boolean sendReadInfo(String sessionId, ImMessage message) {
        String[] ids = sessionId.split(":");
        if (ids.length != 2) {
            throw new IllegalArgumentException("SessionId [" + sessionId + "] is not correct.");
        }
        String targetSessionId = ids[1] + ":" + ids[0];
        if (webSocketMap.containsKey(targetSessionId)) {
            IMWebSocketService webSocketService = webSocketMap.get(targetSessionId);
            try {
                webSocketService.sendReadMessage(message);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取用户中文名
     *
     * @param userId
     * @return
     */
    public String getUserInfo(String userId) {
        return redisTemplate.opsForValue().get(USER_INFO + userId);
    }

    /**
     * 更新用户信息
     *
     * @param userId
     * @param userName
     */
    public void updateUserInfo(String userId, String userName) {
        redisTemplate.opsForValue().set((USER_INFO + userId), userName);
    }

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    public boolean isOnlineUser(String userId) {
        return redisTemplate.opsForSet().isMember(USER_ONLINE_LIST, userId);
    }

    /**
     * 获取有联系的用户id列表
     *
     * @param userId
     * @return
     */
    public Set<String> getRelatedUsers(String userId) {
        return redisTemplate.opsForSet().members(IM_CONNECT_USER + userId);
    }

    /**
     * 获取用户离线消息数
     *
     * @param userId
     * @return
     */
    public String countOfflineMessage(String userId) {
        String offlineNum = redisTemplate.opsForValue().get(USER_OFFLINE_MSG_CNT + "userId");
        if (Objects.isNull(offlineNum)) {
            return "0";
        }
        return offlineNum;
    }

    /**
     * 获取用户发送给用户toUserId 的离线消息数
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public long countOfflineMessage(String fromUserId, String toUserId) {
        String sessionId = this.getSessionId(fromUserId, toUserId);
        return getOfflineLength(sessionId);
    }

    private long getOfflineLength(String sessionId) {
        return redisTemplate.opsForList().size(SESSION_OFFLINE_MESSAGE + sessionId);
    }

    /**
     * 清除用户的离线消息
     *
     * @param sessionId
     * @param userId
     */
    private void clearOfflineMessages(String sessionId, String userId) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        long count = listOperations.size(SESSION_OFFLINE_MESSAGE + sessionId);
        // 清空离线消息
        listOperations.trim(SESSION_OFFLINE_MESSAGE + sessionId, 0, -1);
        // 减少离线消息数
        redisTemplate.opsForValue().increment(USER_OFFLINE_MSG_CNT + userId, -count);
    }

    public void updateLoginUser(String userId, String token) {
        System.out.println(redisTemplate.opsForSet());
        redisTemplate.opsForSet().add(USER_ONLINE_LIST, userId);
        redisTemplate.opsForSet().add(USER_TOKEN_SET, token);
        redisTemplate.opsForValue().set(USER_TOKEN + token, userId);
    }

    public Set<String> getLoginUsers() {
        Set<String> onlineUsers = redisTemplate.opsForSet().members(USER_ONLINE_LIST);
        if (Objects.nonNull(onlineUsers)) {
            return onlineUsers;
        }
        return Collections.emptySet();
    }

    /**
     * 新增用户
     *
     * @param webSocketService
     * @return
     */
    public void addUser(IMWebSocketService webSocketService) {
        webSocketMap.put(webSocketService.getSessionId(), webSocketService);

    }

    public void delUser(IMWebSocketService webSocketService) {
        webSocketMap.remove(webSocketService.getSessionId());
        redisTemplate.opsForSet().remove(USER_ONLINE_LIST, webSocketService.getUserId());
    }

    /**
     * 指定会话是否在线
     *
     * @param sessionId
     */
    private boolean isOnlineSession(String sessionId) {
        if (webSocketMap.containsKey(sessionId)) {
            return true;
        }
        return false;
    }

    private String getSessionId(String fromUserId, String toUserId) {
        return toUserId + ":" + fromUserId;
    }

    /**
     * 根据会话ID 寻找IMWebSocketService
     *
     * @param sessionId
     * @return
     */
    public IMWebSocketService getWebSocketService(String sessionId) {
        return webSocketMap.getOrDefault(sessionId, null);
    }

    MessageHandler() {
        redisTemplate = ApplicationContextUtil.getStringRedisTemplate();
    }
}
