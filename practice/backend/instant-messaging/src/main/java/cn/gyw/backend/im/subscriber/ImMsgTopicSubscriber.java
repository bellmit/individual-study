package cn.gyw.backend.im.subscriber;

import java.io.IOException;

import cn.gyw.backend.im.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.gyw.backend.im.enums.TopicChannels;
import cn.gyw.backend.im.model.message.ImMessage;
import cn.gyw.backend.im.service.IMWebSocketService;

@Component
public class ImMsgTopicSubscriber extends AbstractSubscriber {

    private final static Logger log = LoggerFactory.getLogger(ImMsgTopicSubscriber.class);

    private RedisTemplate<String, String> redisTemplate;

    public ImMsgTopicSubscriber(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Object messageId) {
        log.debug("im channel [{}] messageID : {}", TopicChannels.IM_MESSAGE_CHANNEL.getChannelName(), messageId);
        System.out.println(redisTemplate);
        String message = redisTemplate.opsForValue().get(messageId);
        ImMessage imMessage = JSONObject.parseObject(message, ImMessage.class);
        this.sendToTargetSession(imMessage.getToSessionId(), imMessage);
    }

    private void sendToTargetSession(String targetSessionId, ImMessage imMessage) {
        IMWebSocketService socketService = MessageHandler.INSTANCE.getWebSocketService(targetSessionId);
        if (socketService == null) {
            // TODO: 发布的一瞬间，目标用户离线
            log.warn("Target user offline!");
            return ;
        }
        try {
            socketService.sendMessage(imMessage);
        } catch (IOException e) {
            log.error("", e);
            // TODO: 若失败应该如何处理
            imMessage.setSendTimes(imMessage.getSendTimes() + 1);
        }
    }
}
