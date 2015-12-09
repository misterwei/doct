package doct.document.xls.command;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsUtil;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * offset_save row_offset cell_offset
 * 把偏移存入row_offset cell_offset
 * @author wei
 *
 */
public class XlsOffsetSaveCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params) throws Exception {
		String[] cmdparts = cmdCtx.getDescriptor();
		int row_offset = XlsUtil.getRowOffset(ctx);
		int cell_offset = XlsUtil.getCellOffset(ctx);
		if(cmdparts.length == 1){
			ctx.put("__saved_row_offset__", row_offset);
			ctx.put("__saved_cell_offset__", cell_offset);
		}else if(cmdparts.length == 2){
			Ognl.setValue(cmdparts[1], ctx, ctx.getRoot(), row_offset);
		}else{
			Ognl.setValue(cmdparts[1], ctx, ctx.getRoot(), row_offset);
			Ognl.setValue(cmdparts[2], ctx, ctx.getRoot(), cell_offset);
		}
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "offset_save";
	}

}
