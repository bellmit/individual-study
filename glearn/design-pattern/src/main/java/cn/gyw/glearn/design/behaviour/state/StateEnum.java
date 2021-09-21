package cn.gyw.glearn.design.behaviour.state;

public enum StateEnum {
	A("plan_a"),
	B("plan_b"),
	C("plan_c"),
	D("plan_d"),
	BAD("");

	private String value;

	private StateEnum(String arg0) {
		this.value = arg0;
	}

	protected String getValue() {
		return value;
	}

	protected void setValue(String value) {
		this.value = value;
	}

	/**
	 * 根据值获得枚举对象
	 * ps:每个枚举都需要定义该方法
	 *
	 * @param value 传入的值
	 * @return
	 */
	public static StateEnum getStateEnum(String value) {
		for(StateEnum se : StateEnum.values()) {
			if (value.equals(se.getValue())) {
				return se;
			}
		}
		return null;
	}
}
