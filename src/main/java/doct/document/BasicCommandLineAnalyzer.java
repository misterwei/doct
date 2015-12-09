package doct.document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础的命令分析器
 * @author wei
 */
public abstract class BasicCommandLineAnalyzer implements CommandLineAnalyzer{
	private String delimiterStart = "\\{%";
	private String delimiterEnd = "%\\}";
	private int idCounter = 0;
	private CommandAnalyzer commandAnalyzer;
	private Pattern linePattern = Pattern.compile(delimiterStart + "(.+?)" + delimiterEnd, Pattern.MULTILINE | Pattern.DOTALL);
	private Pattern commandPattern = Pattern.compile("^([\\w\\d_]+):");
	
	/**
	 * 将文本解析成命令行
	 * @param text
	 * @return
	 */
	public List<CommandLine> analyzeText(String text) {
		TextBlockInfo textInfo = createTextBlockInfo(text);
		Matcher m = linePattern.matcher(text);
		while(m.find()){
			String commandline = m.group(1);
			Matcher commandMatcher = commandPattern.matcher(commandline);
			String lineid = "";
			if(commandMatcher.find()){
				lineid = commandMatcher.group(1);
				commandline = commandMatcher.replaceFirst("");
			}else{
				lineid ="LINE_" + idCounter;
			}
			
			idCounterInc();
			
			if(m.start() ==0 && m.end() == text.length()){
				textInfo.setAllMatch(true);
			}
			
			CommandLine cmdinfo = createCommandLine(lineid, textInfo, m.start(), m.end());
			cmdinfo.setSource(commandline);
			textInfo.addCommandLine(cmdinfo);
			
			if(!commandline.trim().isEmpty()){
				List<String[]> descrs = getCommandAnalyzer().analyze(commandline);
				cmdinfo.getDescriptors().addAll(descrs);
				if(!descrs.isEmpty()){
					String[] part = descrs.get(descrs.size() - 1);
					if("end".equalsIgnoreCase(part[0])){
						break;
					}
				}
			}
			
		}
		return textInfo.getCommandLines();
	}
	
	public CommandAnalyzer getCommandAnalyzer() {
		return commandAnalyzer;
	}

	public void setCommandAnalyzer(CommandAnalyzer commandAnalyzer) {
		this.commandAnalyzer = commandAnalyzer;
	}

	protected TextBlockInfo createTextBlockInfo(String text){
		return new TextBlockInfo(text);
	}
	
	protected CommandLine createCommandLine(String lineid, TextBlockInfo textInfo, int start, int end){
		return new CommandLine(lineid, textInfo, start, end);
	}
	
	protected void idCounterInc(){
		this.idCounter++;
	}
	
	public String getDelimiterStart() {
		return delimiterStart;
	}

	public void setDelimiterStart(String delimiterStart) {
		this.delimiterStart = delimiterStart;
	}

	public String getDelimiterEnd() {
		return delimiterEnd;
	}

	public void setDelimiterEnd(String delimiterEnd) {
		this.delimiterEnd = delimiterEnd;
	}

}
