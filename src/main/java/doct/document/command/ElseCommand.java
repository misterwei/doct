package doct.document.command;

import doct.document.CommandContext;
import ognl.OgnlContext;

public class ElseCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		if(prevCmdCtx.getName().matches("if|elif")){
			Boolean result = (Boolean)prevCmdCtx.get("RESULT");
			if(result){
				prevCmdCtx.setNextCommand("endif");
			}
		}else{
			prevCmdCtx.setNextCommand("endif");
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "else";
	}

}
