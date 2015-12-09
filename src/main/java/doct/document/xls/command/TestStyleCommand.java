package doct.document.xls.command;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;

public class TestStyleCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		//poi workbook
		Workbook workbook = (Workbook)params[0];
		//命令行
		List<CommandLine> lines = (List<CommandLine>)params[1];
		
		//获取偏移后的行列
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		int row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
		int cell_index = cellInfo.getCellIndex(XlsUtil.getRowOffset(ctx));
		
		String[] args = cmdCtx.getDescriptor();
		//args[0] add
		int first = NumberUtils.toInt(args[1]);
		int sec = NumberUtils.toInt(args[2]);
		return first + sec;
	}

	public String getStartName() {
		return "style_test";
	}

}
