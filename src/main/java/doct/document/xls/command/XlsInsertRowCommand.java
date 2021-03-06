package doct.document.xls.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;

public class XlsInsertRowCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		Workbook workbook = (Workbook)params[0];
		List<CommandLine> lines = (List<CommandLine>)params[1];
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		int row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
		int sheetIndex = cellInfo.getSheetIndex();
		
		int insert_num = 1;
		if(cmdparts.length > 1){
			insert_num = NumberUtils.toInt(cmdparts[1]);
		}
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		int last = sheet.getLastRowNum();
		sheet.shiftRows(row_index+1, last + insert_num + 1, 1,true,false);
		updateRowIndex(lines, sheetIndex, cellInfo.getRowIndex() + 1, insert_num);
		return NO_OUTPUT;
	}

	private void updateRowIndex(List<CommandLine> lines, int sheetIndex, int start, int rows){
		List<XlsCellInfo> infos = new ArrayList<XlsCellInfo>();
		for(CommandLine line : lines){
			XlsCellInfo cellInfo = (XlsCellInfo)line.getTextInfo();
			if(cellInfo.getSheetIndex() == sheetIndex){
				if(cellInfo.getRowIndex() >= start && !infos.contains(cellInfo)){
					infos.add(cellInfo);
				}
			}
		}
		for(XlsCellInfo cellInfo : infos){
			cellInfo.setRowIndex(cellInfo.getRowIndex() + rows);
		}
	}
	
	public String getStartName() {
		return "insert_row";
	}


}
