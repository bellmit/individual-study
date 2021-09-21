package cn.gyw.components.autodb.model;

import java.io.Serializable;
import java.util.List;

/**
 * 表定义
 */
public class TableDefinition implements Serializable {

	private static final long serialVersionUID = 6219853612832282331L;

	private String name;
	private String encoding;
	private String engine;
	private List<ColumnDefinition> columns;

	public TableDefinition() {
		super();
	}

	public TableDefinition(String name, String encoding, String engine, List<ColumnDefinition> columns) {
		super();
		this.name = name;
		this.encoding = encoding;
		this.engine = engine;
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "TableDefinition [name=" + name + ", encoding=" + encoding + ", engine=" + engine + ", columns="
				+ columns + "]";
	}

}
