package doct.document.xls.command;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
 * {%style row cell cells%}
 * {%style id cells%}
 * {%style id%}
 * @author wei
 *
 */
public class XlsStyleCommand extends AbstractCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, CommandContext prev, Object... params) throws Exception {
		XlsCellInfo cellInfo = (XlsCellInfo)cmdCtx.getCommandLine().getTextInfo();
		int row_index = cellInfo.getRowIndex(XlsUtil.getRowOffset(ctx));
		int cell_index = cellInfo.getCellIndex(XlsUtil.getCellOffset(ctx));
		
		String[] cmdparts = cmdCtx.getDescriptor();
		
		Workbook workbook = (Workbook)params[0];
		List<CommandLine> lines = (List<CommandLine>)params[1];
		Sheet sheet = workbook.getSheetAt(cellInfo.getSheetIndex());
		Row row = sheet.getRow(row_index);
		
		if(row == null)
			row = sheet.createRow(row_index);
		
		int fromRow_index = -1;
		int fromCell_index = -1;
		int fromSheet_index = -1;
		int cells = 1;
		if(cmdparts.length == 2){
			String cellId = cmdparts[1];
			for(CommandLine line : lines){
				if(line.getId().equals(cellId)){
					XlsCellInfo styleCellInfo = (XlsCellInfo)line.getTextInfo();
					fromCell_index = styleCellInfo.getCellIndex();
					fromRow_index = styleCellInfo.getRowIndex();
					fromSheet_index = styleCellInfo.getSheetIndex();
					break;
				}
			}
		}else if(cmdparts.length == 3){
			String cellId = cmdparts[1];
			cells = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			for(CommandLine line : lines){
				if(line.getId().equals(cellId)){
					XlsCellInfo styleCellInfo = (XlsCellInfo)line.getTextInfo();
					fromCell_index = styleCellInfo.getCellIndex();
					fromRow_index = styleCellInfo.getRowIndex();
					fromSheet_index = styleCellInfo.getSheetIndex();
					break;
				}
			}
		}else if(cmdparts.length == 4){
			fromRow_index = (Integer)Ognl.getValue(cmdparts[1], ctx, ctx.getRoot());
			fromCell_index = (Integer)Ognl.getValue(cmdparts[2], ctx, ctx.getRoot());
			cells = (Integer)Ognl.getValue(cmdparts[3], ctx, ctx.getRoot());
			fromSheet_index = cellInfo.getSheetIndex();
		}
		
		Sheet styleSheet = workbook.getSheetAt(fromSheet_index);
		Row styleRow = styleSheet.getRow(fromRow_index);
		Cell styleCell = styleRow.getCell(fromCell_index);
		
		CellStyle style = styleCell.getCellStyle();
		
		
		for(int i=0;i<cells; i++){
			Cell cell = row.getCell(cell_index+i);
			if(cell == null)
				cell = row.createCell(cell_index+i);
			
			cell.setCellStyle(style);
		}
		return NO_OUTPUT;
	}

	public String getStartName() {
		return "style";
	}

}
