package doct.document.xls;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import doct.document.BasicCommandAnalyzer;
import doct.document.CommandAnalyzer;
import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.CommandLineAnalyzer;
import doct.document.DeclaredCommand;
import doct.document.DocumentRenderExecutor;
import doct.document.TextBlockRender;
import doct.document.command.BreakCommand;
import doct.document.command.ContinueCommand;
import doct.document.command.ElifCommand;
import doct.document.command.ElseCommand;
import doct.document.command.EndCommand;
import doct.document.command.FmtNumberCommand;
import doct.document.command.ForCommand;
import doct.document.command.GetCommand;
import doct.document.command.IfCommand;
import doct.document.command.SetCommand;
import doct.document.xls.command.TestAddCommand;
import doct.document.xls.command.XlsCopyCommand;
import doct.document.xls.command.XlsHeightCommand;
import doct.document.xls.command.XlsInsertRowCommand;
import doct.document.xls.command.XlsMergeCommand;
import doct.document.xls.command.XlsOffsetAddCommand;
import doct.document.xls.command.XlsOffsetCommand;
import doct.document.xls.command.XlsOffsetRestoreCommand;
import doct.document.xls.command.XlsOffsetSaveCommand;
import doct.document.xls.command.XlsRemoveCommand;
import doct.document.xls.command.XlsRemoveSheetCommand;
import doct.document.xls.command.XlsStyleCommand;
import doct.document.xls.command.XlsUnmergeCommand;
import doct.document.xls.command.XlsWidthCommand;
import ognl.OgnlContext;

public class XlsDocumentRenderExecutor extends DocumentRenderExecutor {
	private Workbook workbook;
	private static List<DeclaredCommand> commands = new ArrayList<DeclaredCommand>();
	private XlsCellRender render;
	private List<CommandLine> commandLines;
	
	static{
		commands.addAll(BasicCommandAnalyzer.BASIC_COMMANDS);
		commands.add(new XlsInsertRowCommand());
		commands.add(new XlsOffsetAddCommand());
		commands.add(new XlsOffsetCommand());
		commands.add(new XlsOffsetSaveCommand());
		commands.add(new XlsOffsetRestoreCommand());
		commands.add(new XlsStyleCommand());
		commands.add(new XlsRemoveCommand());
		commands.add(new XlsRemoveSheetCommand());
		commands.add(new XlsMergeCommand());
		commands.add(new XlsUnmergeCommand());
		commands.add(new XlsHeightCommand());
		commands.add(new XlsWidthCommand());
		commands.add(new XlsCopyCommand());
		commands.add(new TestAddCommand());
		
	}
	
	private XlsCommandAnalyzer commandAnalyzer;
	private XlsCommandLineAnalyzer commandLineAnalyzer;
	public XlsDocumentRenderExecutor(Workbook workbook){
		this.workbook = workbook;
		render = new XlsCellRender(workbook);
		commandAnalyzer = new XlsCommandAnalyzer(commands);
		this.commandLineAnalyzer = new XlsCommandLineAnalyzer(workbook);
	}
	
	@Override
	protected void clear(OgnlContext ctx, List<CommandLine> lines) throws Exception {
		this.commandLines = lines;
		super.clear(ctx, lines);
	}
	
	protected Object doCommand(DeclaredCommand cmd, OgnlContext context, CommandContext cmdCtx) throws Exception{
		return cmd.doCommand(context, cmdCtx, workbook, commandLines);
	}

	@Override
	protected CommandLineAnalyzer getCommandLineAnalyzer() {
		return commandLineAnalyzer;
	}
	
	@Override
	protected TextBlockRender getTextBlockRender() {
		return render;
	}

	@Override
	protected CommandAnalyzer getCommandAnalyzer() {
		return commandAnalyzer;
	}

}
