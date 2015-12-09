package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%get aa%}
 * @author wei
 *
 */
public class GetCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params)  throws Exception {
		String expr = cmdCtx.getDescriptor()[1];
		return Ognl.getValue(expr, ctx, ctx.getRoot());
	}

	public String getStartName() {
		return "get";
	}

	public List<String[]> analyze(String[] parts) {
		List<String[]> descriptors = new ArrayList<String[]>();
		descriptors.add(parts);
		return descriptors;
	}

}
