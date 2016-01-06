package doct.document.command;

import java.util.Arrays;

import doct.document.CommandContext;
import ognl.OgnlContext;

public class ElseCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		CommandContext parent = cmdCtx.getParentCommand();
		Boolean result = (Boolean)parent.get("RESULT");
		if(result == null)
			throw new Exception("父命令中没有RESULT: "+Arrays.toString(parent.getDescriptor()));
		if(result){
			cmdCtx.setNextCommand("endif");
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "else";
	}

}
