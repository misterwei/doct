package doct.document.xls;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import doct.document.CommandContext;
import doct.document.CommandLine;
import doct.document.DeclaredCommand;
import doct.document.DocumentRenderExecutor;
import doct.document.TextBlockRender;
import ognl.OgnlContext;

public class XlsDocumentRenderExecutor extends DocumentRenderExecutor {
	private Workbook workbook;
	private List<DeclaredCommand> commands = null;
	private XlsCellRender render;
	private List<CommandLine> commandLines;
	public XlsDocumentRenderExecutor(Workbook workbook){
		this.workbook = workbook;
		render = new XlsCellRender(workbook);
	}
	
	protected Object doCommand(DeclaredCommand cmd, OgnlContext context, CommandContext cmdCtx, CommandContext prevCmdCtx) throws Exception{
		return cmd.doCommand(context, cmdCtx, prevCmdCtx, workbook, commandLines);
	}

	@Override
	protected List<CommandLine> getCommandLines() {
		return commandLines;
	}

	@Override
	protected List<DeclaredCommand> getDeclaredCommands() {
		return commands;
	}

	public void setDeclaredCommands(List<DeclaredCommand> commands){
		this.commands = commands;
	}
	
	@Override
	protected TextBlockRender getTextBlockRender() {
		return render;
	}

	public void setCommandLines(List<CommandLine> commandLines) {
		this.commandLines = commandLines;
	}

}
