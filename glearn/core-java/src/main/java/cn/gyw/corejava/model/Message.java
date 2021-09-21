package cn.gyw.corejava.model;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlType(name="aaa",propOrder={"msgId","content","createDate"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Message")
public class Message {
	@XmlElement(name="MsgID")
	private Integer msgId;

    @XmlElement(name="Content")
    private String content;

    @XmlElement(name="CreateDate")
    private Date createDate;

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
