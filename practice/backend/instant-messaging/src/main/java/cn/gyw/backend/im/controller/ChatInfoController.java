package cn.gyw.backend.im.controller;

import cn.gyw.backend.im.handler.MessageHandler;
import cn.gyw.backend.im.model.message.ImMessage;
import cn.gyw.backend.im.model.message.ImMessager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 聊天室
 */
@RestController
@RequestMapping(path = "chat-info")
public class ChatInfoController {

    private final static Logger log = LoggerFactory.getLogger(ChatInfoController.class);

    @GetMapping(path = "/offlineCount/{userId}")
    public String countOfflineMessage(@PathVariable(value = "userId") String userId) {
        log.info("查询用户：{} 离线消息数", userId);
        beat(userId);
        return MessageHandler.INSTANCE.countOfflineMessage(userId);
    }

    @GetMapping(path = "/onlineUsers")
    public Set<String> listOnlineUsers() {
        log.info("查询在线用户列表");
        return MessageHandler.INSTANCE.getLoginUsers();
    }

    @GetMapping(path = "/offlineCount/{userId}/{fromUserId}")
    public long countOfflineMsgFromUser(@PathVariable("userId") String userId,
                                        @PathVariable("fromUserId") String fromUserId) {
        log.info("current user :{}, from user: {}", userId, fromUserId);
        return MessageHandler.INSTANCE.countOfflineMessage(fromUserId, userId);
    }

    @GetMapping("/messagers/{userId}")
    public List<ImMessager> getConnectMessagers(@PathVariable("userId") String userId) {
        final List<ImMessager> list = new ArrayList<>();
        MessageHandler.INSTANCE.getRelatedUsers(userId).forEach((toUserId) -> {
            ImMessager imMessager = ImMessager.builder().userId(userId)
                    .offlineMsgCount(MessageHandler.INSTANCE.countOfflineMessage(userId, toUserId))
                    .isOnline(MessageHandler.INSTANCE.isOnlineUser(userId))
                    .userName(MessageHandler.INSTANCE.getUserInfo(userId))
                    .lastMsg(MessageHandler.INSTANCE.getLastMessage(userId, toUserId))
                    .build();
            list.add(imMessager);
        });
        return list;
    }

    @GetMapping("/hisMessage/{userId}/{messagerId}")
    public List<ImMessage> queryHisMessages(String userId, String toUserId, int start, int end) {
        log.info("查询历史消息列表");
        List<ImMessage> messages = MessageHandler.INSTANCE.getHisMessages(userId, toUserId, start, end);
        return messages;
    }

    private void beat(String userId) {
        // TODO : token
        String token = UUID.randomUUID().toString();
        MessageHandler.INSTANCE.updateLoginUser(userId, token.substring(0, 10));
    }
}
