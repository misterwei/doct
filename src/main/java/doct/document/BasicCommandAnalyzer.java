package doct.document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import doct.document.command.BreakCommand;
import doct.document.command.ContinueCommand;
import doct.document.command.ElifCommand;
import doct.document.command.ElseCommand;
import doct.document.command.EndCommand;
import doct.document.command.FmtDateCommand;
import doct.document.command.FmtNumberCommand;
import doct.document.command.ForCommand;
import doct.document.command.GetCommand;
import doct.document.command.IfCommand;
import doct.document.command.SetCommand;

public class BasicCommandAnalyzer implements CommandAnalyzer{
	public static final List<DeclaredCommand> BASIC_COMMANDS = new ArrayList<DeclaredCommand>();
	
	private static final char[] DELIMITER	= new char[]{'\'', '"'};
	private static final char COMMAND_SPLIT = ';';
	static{
		BASIC_COMMANDS.add(new GetCommand());
		BASIC_COMMANDS.add(new SetCommand());
		BASIC_COMMANDS.add(new ForCommand());
		BASIC_COMMANDS.add(new IfCommand());
		BASIC_COMMANDS.add(new ElseCommand());
		BASIC_COMMANDS.add(new ElifCommand());
		BASIC_COMMANDS.add(new BreakCommand());
		BASIC_COMMANDS.add(new ContinueCommand());
		BASIC_COMMANDS.add(new EndCommand());
		BASIC_COMMANDS.add(new FmtNumberCommand());
		BASIC_COMMANDS.add(new FmtDateCommand());
	}
	
	private Pattern p = Pattern.compile("^[\r\n\\s]*(\\w+)[\\s\t]*");
	
	public List<CommandDescriptor> analyze(String line) {
		List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
		List<String> cmds = splitCommands(line);
		for(int i=0;i<cmds.size();i++){
			String fullcmd = cmds.get(i).trim();
			if(fullcmd.length() == 0)
				continue;
			Matcher m = p.matcher(fullcmd);
			if(!m.find())
				continue;
			String[] parts = null;
			if(m.end() == fullcmd.length()){
				parts = new String[]{m.group(1)};
			}else{
				parts = new String[]{m.group(1), m.replaceFirst("")};
			}
			if("end".equalsIgnoreCase(parts[0])){
				descriptors.add(new CommandDescriptor(parts[0]));
				break;
			}
			List<DeclaredCommand> commands = getCommands();
			for(DeclaredCommand cmd : commands){
				if(parts[0].equals(cmd.getStartName()) || parts[0].equals(DocumentUtils.getEndName(cmd))){
					List<CommandDescriptor> descrs = cmd.analyze(parts);
					if(descrs != null){
						//设置命令对应的定义
						for(CommandDescriptor descr : descrs){
							String part0 = descr.getCommand();
							if(part0.equals(cmd.getStartName()) || part0.equals(DocumentUtils.getEndName(cmd))){
								descr.setDeclaredCommand(cmd);
							}else{
								for(DeclaredCommand __cmd : commands){
									if(part0.equals(__cmd.getStartName()) || part0.equals(DocumentUtils.getEndName(__cmd))){
										descr.setDeclaredCommand(__cmd);
										break;
									}
								}
							}
							if(descr.getDeclaredCommand() == null){
								throw new RuntimeException("not support command : " + descr);
							}
						}
						
						descriptors.addAll(descrs);
						break;
					}
				}
				
			}
		}
		return descriptors;
	}
	
	/**
	 * 将一个命令行拆分出多个命令
	 * @param line
	 * @return
	 */
	protected List<String> splitCommands(String line){
		List<String> commands = new ArrayList<String>();
		//目前只考虑 ’” 两种情况，并且不能嵌套。所以按简单处理
		Character currentDelimiter = null;
		int start = 0;
		for(int i=0;i<line.length();i++){
			char c = line.charAt(i);
			for(char de : DELIMITER){
				if(de == c){
					if(currentDelimiter != null && currentDelimiter.equals(c)){
						currentDelimiter = null;
					}else{
						currentDelimiter = c;
					}
					break;
				}
			}
			if(currentDelimiter == null && c == COMMAND_SPLIT){
				String command = line.substring(start, i);
				commands.add(command);
				start = i + 1;
			}
		}
		//最后一个命令可能没有加 ;
		if(start < line.length()){
			String command = line.substring(start);
			commands.add(command);
		}
		
		return commands;
	}
	
	public List<DeclaredCommand> getCommands() {
		return BASIC_COMMANDS;
	}
	
}
