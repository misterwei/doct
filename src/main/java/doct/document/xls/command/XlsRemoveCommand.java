package doct.document.xls.command;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.command.AbstractCommand;
import doct.document.xls.XlsCellInfo;
import doct.document.xls.XlsUtil;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%remove row cell cells%} 从 row，cell开始移除几个
 * {%remove id cells%} 从ID开始移除几个
 * {%remove cells%} 当前开始移除几个
 * {%remove%}
 * @author wei
 *
 */
public class XlsRemoveCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params) throws Exception {
		Workbook workbook = (Workbook)params[0];
		List<CommandLine> lines = (List<CommandLine>)params[1];
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		String[] cmdparts = cmdCtx.getDescriptor();
		
		int row_index = -1;
		int cell_index = -1;
		int sheet_index = -1;
		int cells = 1;
		if(cmdparts.length == 1){
			row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
			cell_index = cellInfo.getCellIndex(XlsUtil.getCellOffset(ctx));
			sheet_index = cellInfo.getSheetIndex();
			
		}else if(cmdparts.length == 2){
			cells = (Integer)Ognl.getValue(cmdparts[1],ctx, ctx.getRoot());
		}else if(cmdparts.length == 3){
			String cellId = cmdparts[1];
			for(CommandLine line : lines){
				if(line.getId().equals(cellId)){
					XlsCellInfo styleCellInfo = (XlsCellInfo)line.getTextInfo();
					row_index = styleCellInfo.getRowIndex();
					cell_index = styleCellInfo.getCellIndex();
					sheet_index = styleCellInfo.getSheetIndex();
					break;
				}
			}
			cells = (Integer)Ognl.getValue(cmdparts[2],ctx, ctx.getRoot());
		}else if(cmdparts.length == 4){
			row_index = (Integer)Ognl.getValue(cmdparts[1], ctx, ctx.getRoot());
			cell_index = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			cells = (Integer)Ognl.getValue(cmdparts[3],ctx, ctx.getRoot());
			sheet_index = cellInfo.getSheetIndex();
		}
		
		if(row_index == -1){
			return NO_OUTPUT;
		}
		
		Sheet styleSheet = workbook.getSheetAt(sheet_index);
		Row styleRow = styleSheet.getRow(row_index);
		for(int i=0;i<cells;i++){
			Cell styleCell = styleRow.getCell(cell_index + i);
			if(styleCell != null)
				styleRow.removeCell(styleCell);
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "remove";
	}

}
