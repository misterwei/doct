package doct.document.xls.command;

import org.apache.commons.lang3.math.NumberUtils;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import ognl.OgnlContext;

public class TestAddCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		String[] args = cmdCtx.getDescriptor();
		//args[0] add
		int first = NumberUtils.toInt(args[1]);
		int sec = NumberUtils.toInt(args[2]);
		return first + sec;
	}
	
	public String getStartName() {
		return "add";
	}

}
