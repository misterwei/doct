package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import doct.document.CommandDescriptor;
import ognl.Ognl;
import ognl.OgnlContext;

public class ElifCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		CommandContext parent = cmdCtx.getParentCommand();
		if(!parent.getName().matches("if|elif")){
			cmdCtx.setNextCommand("endif");
			return NO_OUTPUT;
		}
		
		Boolean result = (Boolean)parent.get("RESULT");
		if(result){
			cmdCtx.setNextCommand("endif");
			return NO_OUTPUT;
		}
		
		String[] descriptor = cmdCtx.getDescriptor();
		result = (Boolean)Ognl.getValue(descriptor[1], ctx, ctx.getRoot());
		if(!result){
			cmdCtx.setNextCommand("elif|else|endif");
		}
		cmdCtx.put("RESULT", result);
		
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "elif";
	}

	public List<CommandDescriptor> analyze(String[] parts) {
		List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
		descriptors.add(new CommandDescriptor(parts));
		return descriptors;
	}
}
