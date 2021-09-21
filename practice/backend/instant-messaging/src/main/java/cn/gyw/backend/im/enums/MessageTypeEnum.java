package cn.gyw.backend.im.enums;

/**
 * 即时通信-消息内容枚举值
 */
public enum MessageTypeEnum {

    MSG_NOMAL(10001, "普通消息"),
    MSG_READ(10010, "消息已读"),
    HIS_MSG_OVER(20001, "历史消息结束"),
    ;

    private int code;
    private String desc;

    MessageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
