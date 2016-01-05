package doct.document.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doct.document.CommandContext;
import doct.document.CommandDescriptor;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * fmt_date dd 'yyyy-MM-dd'
 * @author wei
 */
public class FmtDateCommand  extends AbstractCommand {

	@Override
	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params)
			throws Exception {
		String[] descrs = cmdCtx.getDescriptor();
		String expr = descrs[1];
		String fmtStr = descrs[2];
		Date date = (Date)Ognl.getValue(expr, ctx, ctx.getRoot());
		String fmt = (String)Ognl.getValue(fmtStr, ctx, ctx.getRoot());
		
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	@Override
	public String getStartName() {
		return "fmt_date";
	}

	public List<CommandDescriptor> analyze(String[] parts) {
		List<CommandDescriptor> cmds = new ArrayList<CommandDescriptor>();
		String[] subparts = parts[1].split("\\s+", 2);
		String[] newparts = new String[subparts.length + 1];
		newparts[0] = parts[0];
		for(int i=0;i<subparts.length;i++){
			newparts[i+1] = subparts[i];
		}
		cmds.add(new CommandDescriptor(newparts));
		return cmds;
	}
	
}
