package doct.document.xls.command;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsUtil;
import ognl.Ognl;
import ognl.OgnlContext;

public class XlsOffsetAddCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		String[] cmdparts = cmdCtx.getDescriptor();
		int old_row_offset = XlsUtil.getRowOffset(ctx);
		Integer _row_offset = (Integer)Ognl.getValue(cmdparts[1], ctx, ctx.getRoot());
		XlsUtil.setRowOffset(ctx, old_row_offset + _row_offset);
		if(cmdparts.length > 2){
			int old_cel_offset = XlsUtil.getCellOffset(ctx);
			Integer _cel_offset = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			XlsUtil.setCellOffset(ctx, old_cel_offset + _cel_offset);
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "offset_add";
	}

}
