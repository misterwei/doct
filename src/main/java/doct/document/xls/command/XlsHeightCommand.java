package doct.document.xls.command;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;

/**
 * {%height 280%}
 * @author wei
 *
 */
public class XlsHeightCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		
		int row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
		
		short height = NumberUtils.toShort(cmdparts[1]);
		
		Workbook workbook = (Workbook)params[0];
		Sheet sheet = workbook.getSheetAt(cellInfo.getSheetIndex());
		Row row = sheet.getRow(row_index);
		if(row != null)
			row = sheet.createRow(row_index);
		row.setHeight(height);
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "height";
	}

}
