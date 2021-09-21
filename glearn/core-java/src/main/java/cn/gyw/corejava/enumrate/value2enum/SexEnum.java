package cn.gyw.corejava.enumrate.value2enum;

public enum SexEnum implements EnumMessage {
	MAN("M",0),
	WOMEN("F",1);

	private String name;
	private int code;

	private SexEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public Object getValue() {
		//此处需要根据枚举对象的哪个属性返回枚举对象，就return该属性
		return code;
	}
}
