package cn.gyw.backend.im.enums;

/**
 * 消息动作
 */
public enum MsgActionEnum {

    CONNECT(1, "第1次或重连初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友");

    private final Integer type;

    private final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
