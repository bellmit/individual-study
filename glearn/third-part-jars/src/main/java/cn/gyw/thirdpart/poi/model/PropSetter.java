package cn.gyw.thirdpart.poi.model;

public class PropSetter {

	private String rOne;// 第一行标题

	private String rTwo;// 第二行标题

	private String prop;// 字段值

	private String type;// 数据类型(缺省)

	private int width;// 单元格宽度

	private boolean color;// 单元格颜色(资源核查用到)

	public String getrOne() {
		return rOne;
	}

	public void setrOne(String rOne) {
		this.rOne = rOne;
	}

	public String getrTwo() {
		return rTwo;
	}

	public void setrTwo(String rTwo) {
		this.rTwo = rTwo;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PropSetter(String rOne, String rTwo, String prop, int width) {
		super();
		this.rOne = rOne;
		this.rTwo = rTwo;
		this.prop = prop;
		this.width = width;
	}

	public PropSetter(String rOne, String rTwo, String prop, int width,
			boolean color) {
		super();
		this.rOne = rOne;
		this.rTwo = rTwo;
		this.prop = prop;
		this.width = width;
		this.color = color;
	}

	public PropSetter(String rOne, String prop, int width) {
		super();
		this.rOne = rOne;
		this.prop = prop;
		this.width = width;
	}

	public PropSetter(String rOne, String rTwo, String prop, String type,
			int width) {
		super();
		this.rOne = rOne;
		this.rTwo = rTwo;
		this.prop = prop;
		this.type = type;
		this.width = width;
	}

	public PropSetter() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropSetter [rOne=");
		builder.append(rOne);
		builder.append(", prop=");
		builder.append(prop);
		builder.append(", width=");
		builder.append(width);
		builder.append("]");
		return builder.toString();
	}

}