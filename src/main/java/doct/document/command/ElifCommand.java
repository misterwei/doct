package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import ognl.Ognl;
import ognl.OgnlContext;

public class ElifCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		if(!prevCmdCtx.getName().matches("if|elif")){
			prevCmdCtx.setNextCommand("endif");
			return NO_OUTPUT;
		}
		
		Boolean result = (Boolean)prevCmdCtx.get("RESULT");
		if(result){
			prevCmdCtx.setNextCommand("endif");
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

	public List<String[]> analyze(String[] parts) {
		List<String[]> descriptors = new ArrayList<String[]>();
		descriptors.add(parts);
		return descriptors;
	}
}
