package cn.gyw.backend.im.netty;

import java.io.Serializable;

public class DataContent implements Serializable {
    // 动作类型
    private Integer action;
    // 用户聊天内容
    private ChatMsg chatMsg;
    // 扩展字段
    private String extend;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
