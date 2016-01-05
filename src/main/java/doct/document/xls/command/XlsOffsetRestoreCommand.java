package doct.document.xls.command;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsUtil;
import ognl.OgnlContext;
/**
 * offset_restore
 * @author wei
 *
 */
public class XlsOffsetRestoreCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		Integer row_offset = (Integer)ctx.get("__saved_row_offset__");
		Integer cell_offset = (Integer)ctx.get("__saved_cell_offset__");
		
		if(row_offset == null || cell_offset == null)
			throw new Exception("没有找到备份的offset，无法恢复");
		
		XlsUtil.setRowOffset(ctx, row_offset);
		XlsUtil.setCellOffset(ctx, cell_offset);
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "offset_restore";
	}

}
