package doct.document.xls.command;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;

/**
 * {%width 280%}
 * @author wei
 *
 */
public class XlsWidthCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		
		int cell_index = cellInfo.getCellIndex(XlsUtil.getCellOffset(ctx));
		
		int width = NumberUtils.toInt(cmdparts[1]);
		
		Workbook workbook = (Workbook)params[0];
		Sheet sheet = workbook.getSheetAt(cellInfo.getSheetIndex());
		sheet.setColumnWidth(cell_index, width);
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "width";
	}

}
