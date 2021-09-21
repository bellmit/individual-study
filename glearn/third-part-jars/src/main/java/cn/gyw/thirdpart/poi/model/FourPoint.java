package cn.gyw.thirdpart.poi.model;

//单元格合并坐标
public class FourPoint {

	private int rowStart;

	private int rowEnd;

	private int colStart;

	private int colEnd;

	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public int getColStart() {
		return colStart;
	}

	public void setColStart(int colStart) {
		this.colStart = colStart;
	}

	public int getColEnd() {
		return colEnd;
	}

	public void setColEnd(int colEnd) {
		this.colEnd = colEnd;
	}

	public FourPoint() {
		super();
	}

	public FourPoint(int rowStart, int rowEnd, int colStart, int colEnd) {
		super();
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
		this.colStart = colStart;
		this.colEnd = colEnd;
	}
}
