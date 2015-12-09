package doct.document.command;

import java.text.NumberFormat;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import doct.document.CommandContext;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%fmt_number number [minint fract]%}
 * @author wei
 *
 */
public class FmtNumberCommand extends AbstractCommand {
	static Logger log = LoggerFactory.getLogger(FmtNumberCommand.class);
	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prevCmdCtx, Object... params)
			throws Exception {
		String[] descrs = cmdCtx.getDescriptor();
		String expr = descrs[1];
		int minInt = 1;
		int fract = -1;
		if(descrs.length == 3){
			minInt = NumberUtils.toInt(descrs[2], 1);
		}else if(descrs.length == 4){
			fract = NumberUtils.toInt(descrs[3], 2);
		}
		try{
			Object ognlValue = Ognl.getValue(expr, ctx, ctx.getRoot());
			if(ognlValue != null){
				Double value = NumberUtils.toDouble(String.valueOf(ognlValue));
				NumberFormat numberFmt = NumberFormat.getNumberInstance();
				if(fract != -1){
					numberFmt.setMaximumFractionDigits(fract);
					numberFmt.setMinimumFractionDigits(fract);
				}
				numberFmt.setMinimumIntegerDigits(minInt);
				numberFmt.setGroupingUsed(false);
				return numberFmt.format(value);
			}
		}catch(Exception e){
			log.error("数值格式化错误",e);
		}
		return "";
	}

	public String getStartName() {
		return "fmt_number";
	}

}
