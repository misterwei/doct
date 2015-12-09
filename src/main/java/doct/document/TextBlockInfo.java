package doct.document;

import java.util.ArrayList;
import java.util.List;

public class TextBlockInfo {
	private String text;
	private boolean isAllMatch = false;
	private List<CommandLine> lines = new ArrayList<CommandLine>();
	
	public TextBlockInfo(String text){
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public boolean isAllMatch() {
		return isAllMatch;
	}

	public void setAllMatch(boolean isAllMatch) {
		this.isAllMatch = isAllMatch;
	}

	public void addCommandLine(CommandLine line){
		this.lines.add(line);
	}
	
	public List<CommandLine> getCommandLines() {
		return lines;
	}
	
}
