package doct.document.command;

import doct.document.CommandContext;
import ognl.OgnlContext;

public class ElseCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		CommandContext parent = cmdCtx.getParentCommand();
		if(parent.getName().matches("if|elif")){
			Boolean result = (Boolean)parent.get("RESULT");
			if(result){
				cmdCtx.setNextCommand("endif");
			}
		}else{
			cmdCtx.setNextCommand("endif");
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "else";
	}

}
