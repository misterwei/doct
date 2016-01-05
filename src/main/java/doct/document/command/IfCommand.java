package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import doct.document.CommandDescriptor;
import doct.document.DeclaredCommand;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%if codition %}{%endif%}
 * @author wei
 *
 */
public class IfCommand  implements DeclaredCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		String[] descriptor = cmdCtx.getDescriptor();
		Boolean result = (Boolean)Ognl.getValue(descriptor[1], ctx, ctx.getRoot());
		if(!result){
			cmdCtx.setNextCommand("elif|else|endif");
		}
		cmdCtx.put("RESULT", result);
		return NO_OUTPUT;
	}

	public boolean doEnd(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		return true;
	}

	public String getStartName() {
		return "if";
	}

	public boolean isHasEnd() {
		return true;
	}

	public List<CommandDescriptor> analyze(String[] parts) {
		List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
		descriptors.add(new CommandDescriptor(parts));
		return descriptors;
	}

}
