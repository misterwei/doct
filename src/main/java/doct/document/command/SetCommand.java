package doct.document.command;

import doct.document.CommandContext;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%set name value%}
 * @author wei
 *
 */
public class SetCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)  throws Exception {
		String expr1 = cmdCtx.getDescriptor()[1];
		String expr2 = cmdCtx.getDescriptor()[2];
		Object val = Ognl.getValue(expr2, ctx, ctx.getRoot());
		Ognl.setValue(expr1, ctx, ctx.getRoot(), val);
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "set";
	}

	public String getEndName() {
		return null;
	}

}
