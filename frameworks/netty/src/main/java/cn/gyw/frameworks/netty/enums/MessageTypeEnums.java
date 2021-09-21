package cn.gyw.frameworks.netty.enums;

public enum MessageTypeEnums {

	DEFAULT(0),
	LOGIN(1),
	PEAR_TO_PEAR(2);
	
	private int code;
	
	private MessageTypeEnums(int code) {
		this.code = code;
	}
	
	public static MessageTypeEnums messageType(int code) {
		for (MessageTypeEnums messageType : values()) {
			if (messageType.code == code) {
				return messageType;
			}
		}
		return DEFAULT;
	}
}
