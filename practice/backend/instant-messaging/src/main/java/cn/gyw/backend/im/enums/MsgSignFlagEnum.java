package cn.gyw.backend.im.enums;

/**
 * 消息签收状态
 */
public enum MsgSignFlagEnum {

    UNSIGNED(0, "未签收"),
    SIGNED(1, "已签收");

    private final Integer type;
    private final String content;

    MsgSignFlagEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
