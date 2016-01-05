package doct.document.xls.command;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%unmerge row cell%}
 * {%unmerge id%}
 * {%unmerge%}
 * @author wei
 *
 */
public class XlsUnmergeCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		Workbook workbook = (Workbook)params[0];
		List<CommandLine> lines = (List<CommandLine>)params[1];
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		
		int row_index = -1;
		int cell_index = -1;
		int sheet_index = -1;
		if(cmdparts.length == 1){
			row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
			cell_index = cellInfo.getCellIndex(XlsUtil.getCellOffset(ctx));
			sheet_index = cellInfo.getSheetIndex();
			
		}else if(cmdparts.length == 2){
			String cellId = cmdparts[1];
			for(CommandLine line : lines){
				if(line.getId().equals(cellId)){
					XlsCellInfo styleCellInfo = (XlsCellInfo)line.getTextInfo();
					row_index = styleCellInfo.getRowIndex();
					cell_index = styleCellInfo.getCellIndex();
					sheet_index = styleCellInfo.getSheetIndex();
					break;
				}
			}
		}else if(cmdparts.length == 3){
			row_index = (Integer)Ognl.getValue(cmdparts[1], ctx, ctx.getRoot());
			cell_index = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			sheet_index = cellInfo.getSheetIndex();
		}
		
		Sheet sheet = workbook.getSheetAt(sheet_index);
		List<CellRangeAddress> ranges = sheet.getMergedRegions();
		if(ranges == null)
			return NO_OUTPUT;
		for(int i=0;i<ranges.size();i++){
			CellRangeAddress adress =  ranges.get(i);
			if(adress.getFirstRow() == row_index && adress.getFirstColumn() == cell_index){
				sheet.removeMergedRegion(i);
				break;
			}
		}
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "unmerge";
	}

}
