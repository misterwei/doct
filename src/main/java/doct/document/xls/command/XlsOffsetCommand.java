package doct.document.xls.command;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsUtil;
import ognl.Ognl;
import ognl.OgnlContext;

public class XlsOffsetCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		String[] cmdparts = cmdCtx.getDescriptor();
		Integer _row_offset = (Integer)Ognl.getValue(cmdparts[1], ctx, ctx.getRoot());
		XlsUtil.setRowOffset(ctx, _row_offset);
		if(cmdparts.length > 2){
			Integer _cel_offset = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			XlsUtil.setCellOffset(ctx, _cel_offset);
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "offset";
	}

}
