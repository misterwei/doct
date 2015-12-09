package doct.document.xls;

import java.util.List;

import doct.document.CommandLine;
import ognl.OgnlContext;

public class XlsUtil {
	
	public static int getRowOffset(OgnlContext context) {
		Integer r = (Integer)context.get("__row_offset__");
		if(r == null){
			return 0;
		}
		return r;
	}
	
	public static int getCellOffset(OgnlContext context){
		Integer r = (Integer)context.get("__cel_offset__");
		if(r == null){
			return 0;
		}
		return r;
	}
	
	public static void setRowOffset(OgnlContext context, int value){
		context.put("__row_offset__", value);
	}
	
	public static void setCellOffset(OgnlContext context, int value){
		context.put("__cel_offset__", value);
	}
	
	public static void refreshRowIndex(List<CommandLine> lines, int startRowIndex, int rows){
		for(CommandLine line: lines){
			XlsCommandLine xlsLine = (XlsCommandLine)line;
			int ri = xlsLine.getCellInfo().getRowIndex();
			if(ri >= startRowIndex){
				xlsLine.getCellInfo().setRowIndex(ri + rows);
			}
		}
	}
}
