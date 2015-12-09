package doct.document.command;

import doct.document.CommandContext;
import ognl.OgnlContext;

/**
 * {%end%}
 * @author wei
 *
 */
public class EndCommand extends AbstractCommand {

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params) throws Exception {
		return null;
	}

	public String getStartName() {
		return "end";
	}

}
