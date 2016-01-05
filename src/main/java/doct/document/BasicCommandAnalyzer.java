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
		String[] cmds = line.split(";");
		for(int i=0;i<cmds.length;i++){
			String fullcmd = cmds[i].trim();
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
	
	
	public List<DeclaredCommand> getCommands() {
		return BASIC_COMMANDS;
	}
}
