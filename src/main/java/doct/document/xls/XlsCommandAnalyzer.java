package doct.document.xls;

import java.util.List;

import doct.document.BasicCommandAnalyzer;
import doct.document.DeclaredCommand;

public class XlsCommandAnalyzer extends BasicCommandAnalyzer{

	private List<DeclaredCommand> commands = null;
	public XlsCommandAnalyzer(List<DeclaredCommand> commands) {
		this.commands = commands;
	}
	
	public List<DeclaredCommand> getCommands() {
		return commands;
	}

}
