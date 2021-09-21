package cn.gyw.backend.im.service;

import cn.gyw.backend.im.enums.MessageTypeEnum;
import cn.gyw.backend.im.handler.MessageHandler;
import cn.gyw.backend.im.model.message.ImMessage;
import cn.gyw.platform.common.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket 服务
 */
@Service
@ServerEndpoint(value = "/{userId}/{toUserId}")
public class IMWebSocketService {

    private final static Logger log = LoggerFactory.getLogger(IMWebSocketService.class);

    // 当前在线人数
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    // 当前会话发起用户ID
    private String userId;

    // 当前会话目标用户ID
    private String toUserId;

    // 会话
    private Session session;
    // 会话ID
    private String sessionId;
    
    // 收到消息
    @OnMessage
    public void onMessage(String jsonMessage) {
        log.info("current user [{}] send msg :{}", userId, jsonMessage);
        ImMessage imMessage = JSONObject.parseObject(jsonMessage, ImMessage.class);
        imMessage.setMessageType(MessageTypeEnum.MSG_NOMAL.code());
        imMessage.setUserName(getUserName(imMessage.getUserId()));
        imMessage.setCreatedMills(System.currentTimeMillis());
        imMessage.setUid(imMessage.getUserId() + ":" + imMessage.getCreatedMills());
        imMessage.setSendDateTime(DateUtil.currentDateTime());
        MessageHandler.INSTANCE.handleMessage(imMessage);
        try {
            this.sendMessage(JSONObject.toJSONString(imMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给目标用户发送消息
     * @param imMessage
     */
    public void sendMessage(ImMessage imMessage) throws IOException {
        if (Objects.isNull(imMessage)) {
            log.warn("send to target message is null");
            return ;
        }
        this.sendMessage(JSONObject.toJSONString(imMessage));
    }

    /**
     * 发送消息已读
     * @param imMessage
     * @throws IOException
     */
    public void sendReadMessage(ImMessage imMessage) throws IOException {
        imMessage.setMessageType(MessageTypeEnum.MSG_READ.code());
        imMessage.setContent("消息状态变更:已读");
        imMessage.setCreatedMills(System.currentTimeMillis());
        this.sendMessage(new JSONObject().toJSONString());
    }

    /**
     * 给目标用户发送消息
     * @param msgJson JSON格式消息
     */
    public void sendMessage(String msgJson) throws IOException {
        if (msgJson == null || msgJson.equals("")) {
            log.warn("message content is null");
            return;
        }
        this.session.getBasicRemote().sendText(msgJson);
//        this.session.getAsyncRemote().sendText(msgJson);
        log.info("send message success to {} , msg : {}", this.userId, msgJson);
    }

    // 建立连接
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId,
                       @PathParam(value = "toUserId") String toUserId,
                       Session session) {
        log.info("{} is login! target user : {}", userId, toUserId);
        this.userId = userId;
        this.toUserId = toUserId;
        this.session = session;
        this.sessionId = userId + ":" + toUserId;
        MessageHandler.INSTANCE.addUser(this);
        this.addOnlineCount();
        MessageHandler.INSTANCE.loginAfter(sessionId, userId);
        log.info("{} login succeed...", userId);
    }

    // 连接关闭
    @OnClose
    public void onClosed(Session session) {
        log.info("user [{}] request logout ...", this.userId);
        MessageHandler.INSTANCE.delUser(this);
        this.subOnlineCount();
        log.info("user [{}] is logout. current online user count :{}", this.userId, onlineCount.get());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error:", error);
    }

    // 获取用户ID
    public String getUserId() {
        return userId;
    }
    // 获取 会话唯一ID
    public String getSessionId() {
        return sessionId;
    }
    // TODO : send request get user name
    public String getUserName(String userId) {
        return "seven eleven";
    }

    //private String getUserName(String userId) {
    //    final String sysHost = "http://localhost:8081/system";
    //    StringBuilder builder = new StringBuilder(sysHost);
    //    builder.append("/user/").append(userId);
    //    String result = HttpClientUtil.sendGetRequest(builder.toString());
    //    JSONObject userJson = JSONObject.parseObject(result);
    //    return userJson.getString("firstName") + userJson.getString("secondName");
    //}

    /* ***************inner method**************** */
    // 增加在线用户数
    private void addOnlineCount() {
        onlineCount.incrementAndGet();
    }
    // 减少在线用户数
    private void subOnlineCount() {
        onlineCount.decrementAndGet();
    }

}
