package doct.document.xls.command;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;

/**
 * {%merge 1 2 %}
 * @author wei
 *
 */
public class XlsMergeCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		Workbook workbook = (Workbook)params[0];
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		int row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
		int cell_index = cellInfo.getCellIndex(XlsUtil.getCellOffset(ctx));
		
		if(cmdparts.length < 3){
			throw new Exception("merge命令必须有两个参数(行和列)，无法执行 "+Arrays.toString(cmdparts));
		}
		
		int row_count = NumberUtils.toInt(cmdparts[1], 1);
		int cell_count = NumberUtils.toInt(cmdparts[2], 1);
		
		CellRangeAddress range = new CellRangeAddress(row_index, row_index+row_count, cell_index, cell_index + cell_count);
		Sheet sheet = workbook.getSheetAt(cellInfo.getSheetIndex());
		int mergeIndex = sheet.addMergedRegion(range);
		Row row = sheet.getRow(row_index);
		if(row == null)
			row = sheet.createRow(row_index);
		row.createCell(cell_index);
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "merge";
	}

}
