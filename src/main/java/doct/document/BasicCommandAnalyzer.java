package doct.document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BasicCommandAnalyzer implements CommandAnalyzer{
	private Pattern p = Pattern.compile("^[\r\n\\s]*(\\w+)[\\s\t]*");
	
	public List<String[]> analyze(String line) {
		List<String[]> descriptors = new ArrayList<String[]>();
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
				descriptors.add(parts);
				break;
			}
			List<DeclaredCommand> commands = getCommands();
			for(DeclaredCommand cmd : commands){
				if(parts[0].equals(cmd.getStartName()) || parts[0].equals(cmd.getEndName())){
					List<String[]> descrs = cmd.analyze(parts);
					if(descrs != null){
						descriptors.addAll(descrs);
						break;
					}
				}
				
			}
		}
		return descriptors;
	}

}
