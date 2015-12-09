package doct.document.xls;

import doct.document.TextBlockInfo;

public class XlsCellInfo extends TextBlockInfo {
	private int rowIndex;
	private int cellIndex;
	private int sheetIndex;
	
	public XlsCellInfo(int sheetIndex, int rowIndex, int cellIndex, String text) {
		super(text);
		this.cellIndex = cellIndex;
		this.rowIndex = rowIndex;
		this.sheetIndex = sheetIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	public int getCellIndex() {
		return cellIndex;
	}
	
	public int getRowIndex(int offset) {
		return rowIndex + offset;
	}
	public int getCellIndex(int offset) {
		return cellIndex + offset;
	}
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	@Override
	public String toString() {
		return String.format("<%d,%d,%d>", sheetIndex, rowIndex, cellIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cellIndex;
		result = prime * result + rowIndex;
		result = prime * result + sheetIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XlsCellInfo other = (XlsCellInfo) obj;
		if (cellIndex != other.cellIndex)
			return false;
		if (rowIndex != other.rowIndex)
			return false;
		if (sheetIndex != other.sheetIndex)
			return false;
		return true;
	}

}

