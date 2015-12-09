package doct.document.command;

import java.util.ArrayList;
import java.util.List;

import doct.document.CommandContext;
import doct.document.DeclaredCommand;
import ognl.OgnlContext;

public abstract class AbstractCommand implements DeclaredCommand{

	public boolean doEnd(OgnlContext ctx, CommandContext info, Object... params)  throws Exception {
		return true;
	}

	public List<String[]> analyze(String[] parts) {
		List<String[]> cmds = new ArrayList<String[]>();
		if(parts.length > 1){
			String[] subparts = parts[1].split("\\s+");
			String[] newparts = new String[subparts.length + 1];
			newparts[0] = parts[0];
			for(int i=0;i<subparts.length;i++){
				newparts[i+1] = subparts[i];
			}
			cmds.add(newparts);
		}else{
			cmds.add(parts);
		}
		return cmds;
	}
	
	public String getEndName() {
		return null;
	}
}
