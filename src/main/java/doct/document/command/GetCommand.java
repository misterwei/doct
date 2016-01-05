package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import doct.document.CommandDescriptor;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%get aa%}
 * @author wei
 *
 */
public class GetCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)  throws Exception {
		String expr = cmdCtx.getDescriptor()[1];
		return Ognl.getValue(expr, ctx, ctx.getRoot());
	}

	public String getStartName() {
		return "get";
	}

	public List<CommandDescriptor> analyze(String[] parts) {
		List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
		descriptors.add(new CommandDescriptor(parts));
		return descriptors;
	}

}
