package cn.gyw.backend.im.model.message;

public class ImMessage {

	private Integer messageType;
	private String uid;
	private String userId;
	private String userName;
	private String toUserId;
	private String content;
	// 当前会话id
	private String sessionId;
	// 目标会话id
	private String toSessionId;
	// 是否已读
	private boolean isRead = false;
	// 发送次数
	private int sendTimes;
	private long createdMills;
	// 发送时间
	private String sendDateTime;

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public void read() {
		isRead = true;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getToSessionId() {
		return toSessionId;
	}

	public void setToSessionId(String toSessionId) {
		this.toSessionId = toSessionId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

	public int getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}

	public long getCreatedMills() {
		return createdMills;
	}

	public void setCreatedMills(long createdMills) {
		this.createdMills = createdMills;
	}

	public String getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
}
