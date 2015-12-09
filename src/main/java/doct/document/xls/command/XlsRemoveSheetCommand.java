package doct.document.xls.command;

import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.command.AbstractCommand;
import ognl.OgnlContext;

public class XlsRemoveSheetCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params) throws Exception {
		Workbook workbook = (Workbook)params[0];
		String[] cmdparts = cmdCtx.getDescriptor();
		String sheetIndex = cmdparts[1];
		int index = -1;
		if(sheetIndex.matches("\\d+")){
			index = Integer.parseInt(sheetIndex);
			workbook.removeSheetAt(index);
		}else{
			throw new Exception("remove_sheet 没有找到要移除的Sheet " + sheetIndex);
		}
				
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "remove_sheet";
	}

}
