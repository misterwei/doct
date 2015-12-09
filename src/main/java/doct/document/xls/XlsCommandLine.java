package doct.document.xls;

import doct.document.CommandLine;
import doct.document.TextBlockInfo;

public class XlsCommandLine extends CommandLine {
	
	private XlsCellInfo cellInfo;
	
	public XlsCommandLine(String id, TextBlockInfo textInfo, int start, int end) {
		super(id, textInfo, start, end);
		this.cellInfo = (XlsCellInfo)textInfo;
	}

	public XlsCellInfo getCellInfo() {
		return cellInfo;
	}

	public void setCellInfo(XlsCellInfo cellInfo) {
		this.cellInfo = cellInfo;
	}
	
}
