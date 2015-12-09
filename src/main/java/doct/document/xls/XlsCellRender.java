package doct.document.xls;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandLine;
import doct.document.TextBlockInfo;
import doct.document.TextBlockRender;
import ognl.Ognl;
import ognl.OgnlContext;

public class XlsCellRender implements TextBlockRender{
	private Workbook workbook;
	public XlsCellRender(Workbook workbook) {
		this.workbook = workbook;
	}
	
	public void render(OgnlContext ctx, TextBlockInfo block) throws Exception{
		XlsCellInfo cellInfo = (XlsCellInfo)block;
		Sheet sheet = workbook.getSheetAt(cellInfo.getSheetIndex());
		int row_offset = XlsUtil.getRowOffset(ctx);
		int cell_offset = XlsUtil.getCellOffset(ctx);
		
		int rownum = cellInfo.getRowIndex(row_offset);
		int cellnum = cellInfo.getCellIndex(cell_offset);
		
		Row row = sheet.getRow(rownum);
		if(row == null){
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(cellnum);
		if(cell == null){
			cell = row.createCell(cellnum);
		}
		
		List<CommandLine> commandlines = cellInfo.getCommandLines();
		if(cellInfo.isAllMatch()){
			//倒叙渲染，防止错乱
			for(int i=commandlines.size()-1; i>=0; i--){
				CommandLine cmdinfo = commandlines.get(i);
				Object value = ctx.get("_"+cmdinfo.getId());
				if(value == null){
					cell.setCellType(Cell.CELL_TYPE_BLANK);
					continue;
				}
				if(Integer.class.equals(value.getClass()) ||
						Integer.TYPE.equals(value.getClass())||
						Float.class.equals(value.getClass()) ||
						Float.TYPE.equals(value.getClass())||
						Double.class.equals(value.getClass()) ||
						Double.TYPE.equals(value.getClass())){
					String val = String.valueOf(value);
					cell.setCellValue(NumberUtils.toDouble(val));
				}else if(value instanceof Date){
					Date date = (Date)value;
					cell.setCellValue(date);
				}else{
					String val = String.valueOf(value);
					cell.setCellValue(val);
				}
			}
		}else{
			String source = cellInfo.getText();
			StringBuilder sb = new StringBuilder(source);
			//倒叙渲染，防止错乱
			for(int i=commandlines.size()-1; i>=0; i--){
				CommandLine cmdinfo = commandlines.get(i);
				Object value = Ognl.getValue("#_"+cmdinfo.getId(), ctx, ctx.getRoot());
				String val = "";
				if(value != null){
					val = String.valueOf(value);
				}
				sb.replace(cmdinfo.getStart(), cmdinfo.getEnd(), val);
			}
			cell.setCellValue(sb.toString());
		}
	}

}
