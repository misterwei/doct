package doct.document.command;

import doct.document.CommandContext;
import ognl.OgnlContext;
/**
 * {%continue%}
 * @author wei
 *
 */
public class ContinueCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		ctx.put("__continue__", true);
		cmdCtx.setNextCommand("endfor");
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "continue";
	}

}
