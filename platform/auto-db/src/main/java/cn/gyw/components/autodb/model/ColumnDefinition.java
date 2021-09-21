package cn.gyw.components.autodb.model;

import java.io.Serializable;

/*
 * 列定义
 */
public class ColumnDefinition implements Serializable {

	private static final long serialVersionUID = 5580361407630853504L;

	private String idGenerator;
	private String name;
	private boolean isPrimary = false;
	private String type;
	private int length;
	private String comment;

	private String defaultValue;

	public ColumnDefinition() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(String idGenerator) {
		this.idGenerator = idGenerator;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "ColumnDefinition [idGenerator=" + idGenerator + ", name=" + name + ", isPrimary=" + isPrimary
				+ ", type=" + type + ", length=" + length + ", comment=" + comment + ", defaultValue=" + defaultValue
				+ "]";
	}

}
