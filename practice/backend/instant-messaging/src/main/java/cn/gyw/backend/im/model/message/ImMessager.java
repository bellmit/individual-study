package cn.gyw.backend.im.model.message;

import java.io.Serializable;

/**
 * 消息人
 */
public class ImMessager implements Serializable {
    private static final long serialVersionUID = 8364584398656587206L;

    private String userId;
    private String userName;
    private boolean isOnline;
    private long offlineMsgCount;
    private ImMessage lastMsg;

    public ImMessager() {
    }

    public ImMessager(String userId, String userName, boolean isOnline, long offlineMsgCount, ImMessage lastMsg) {
        this.userId = userId;
        this.userName = userName;
        this.isOnline = isOnline;
        this.offlineMsgCount = offlineMsgCount;
        this.lastMsg = lastMsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getOfflineMsgCount() {
        return offlineMsgCount;
    }

    public void setOfflineMsgCount(long offlineMsgCount) {
        this.offlineMsgCount = offlineMsgCount;
    }

    public ImMessage getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(ImMessage lastMsg) {
        this.lastMsg = lastMsg;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String userName;
        private boolean isOnline;
        private long offlineMsgCount;
        private ImMessage lastMsg;

        public ImMessager build() {
            return new ImMessager(userId, userName, isOnline, offlineMsgCount, lastMsg);
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder offlineMsgCount(long offlineMsgCount) {
            this.offlineMsgCount = offlineMsgCount;
            return this;
        }

        public Builder isOnline(boolean isOnline) {
            this.isOnline = isOnline;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder lastMsg(ImMessage lastMsg) {
            this.lastMsg = lastMsg;
            return this;
        }
    }
}
