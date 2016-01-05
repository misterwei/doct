package doct.document.command;

import doct.document.CommandContext;
import ognl.OgnlContext;
/**
 * {%break%}
 * @author wei
 *
 */
public class BreakCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		ctx.put("__break__", true);
		cmdCtx.setNextCommand("endfor");
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "break";
	}

}
