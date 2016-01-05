package doct.document.xls;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import doct.document.BasicCommandLineAnalyzer;
import doct.document.CommandAnalyzer;
import doct.document.CommandDescriptor;
import doct.document.CommandLine;
import doct.document.TextBlockInfo;

/**
 * Excel 命令行解析器
 * @author wei
 */
public class XlsCommandLineAnalyzer extends BasicCommandLineAnalyzer{
	private Workbook workbook;
	private int _sheetIndex;
	private int _rowIndex;
	private int _cellIndex;
	
	public XlsCommandLineAnalyzer(Workbook workbook){
		this.workbook = workbook;
	}
	
	public List<CommandLine> analyze(CommandAnalyzer commandAnalyzer) {
		int sheets = workbook.getNumberOfSheets();
		List<CommandLine> allCommandList = new ArrayList<CommandLine>();
		
		for(int sheetIndex = 0; sheetIndex < sheets; sheetIndex++){
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if(sheet == null)
				continue;
			
			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			
			TAG_ROW:
			for(int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++){
				Row row = sheet.getRow(rowIndex);
				if(row == null)
					continue;
				int firstCell = row.getFirstCellNum();
				int lastCell = row.getLastCellNum();
				for(int cellIndex = firstCell; cellIndex <= lastCell; cellIndex++){
					Cell cell = row.getCell(cellIndex);
					if(cell == null)
						continue;
					
					if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
						_sheetIndex =  sheetIndex;
						_rowIndex =  rowIndex;
						_cellIndex =  cellIndex;
						
						String text = cell.getStringCellValue();
						List<CommandLine> commandLines = analyze(commandAnalyzer, text);
						allCommandList.addAll(commandLines);
						
						if(!commandLines.isEmpty()){
							CommandLine line = commandLines.get(commandLines.size() - 1);
							List<CommandDescriptor> descrs = line.getDescriptors();
							if(!descrs.isEmpty()){
								CommandDescriptor descr = descrs.get(descrs.size() - 1);
								if("end".equalsIgnoreCase(descr.getCommand())){
									if(cellIndex == 0){
										break TAG_ROW;
									}else{
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return allCommandList;
	}
	
	protected TextBlockInfo createTextBlockInfo(String text){
		return new XlsCellInfo(_sheetIndex, _rowIndex, _cellIndex, text);
	}
	
	protected CommandLine createCommandLine(String lineid, TextBlockInfo textInfo, int start, int end){
		return new XlsCommandLine(lineid, textInfo, start, end);
	}
	
	
}
