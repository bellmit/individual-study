package cn.gyw.frameworks.netty.model;

public class ImProtocol {

	private Long uid;
	
	private Long toId;
	
	private String content;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ImProtocol [uid=" + uid + ", toId=" + toId + ", content=" + content + "]";
	}

}
