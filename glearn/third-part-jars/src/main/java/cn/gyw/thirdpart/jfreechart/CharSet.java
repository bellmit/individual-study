package cn.gyw.thirdpart.jfreechart;

import java.util.Comparator;

public class CharSet implements Comparator<CharSet> {

	private String rowKey;//Y轴

	private String columKey;//X轴

	private Double value;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getColumKey() {
		return columKey;
	}

	public void setColumKey(String columKey) {
		this.columKey = columKey;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public CharSet(String rowKey, String columKey, Double value) {
		super();
		this.rowKey = rowKey;
		this.columKey = columKey;
		this.value = value;
	}

	public CharSet() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CharSet [rowKey=");
		builder.append(rowKey);
		builder.append(", columKey=");
		builder.append(columKey);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compare(CharSet set1, CharSet set2) {
		int flag = set1.getColumKey().compareTo(set2.getColumKey());
		return flag;
	}

}