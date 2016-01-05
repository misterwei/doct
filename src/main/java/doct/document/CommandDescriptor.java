package doct.document;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandDescriptor {
	private static AtomicInteger _id = new AtomicInteger(0);
	private int id;
	private String command;
	private String[] parameter;
	private DeclaredCommand declaredCommand;
	private String[] source;
	public CommandDescriptor(String[] descrs){
		if(descrs == null || descrs.length ==0)
			throw new RuntimeException("命令描述不能是空的");
		this.command = descrs[0];
		if(descrs.length > 1){
			this.parameter = Arrays.copyOfRange(descrs, 1, descrs.length);
		}
		this.id = _id.addAndGet(1);
		this.source = descrs;
	}
	
	public CommandDescriptor(String command,String... parameter){
		this.command  = command;
		this.parameter = parameter;
		this.id = _id.addAndGet(1);
		if(parameter == null){
			this.source = new String[]{command}; 
		}else{
			this.source = new String[parameter.length + 1];
			this.source[0] = command;
			System.arraycopy(parameter, 0, this.source, 1, parameter.length);
		}
		
	}

	public int getId() {
		return id;
	}

	public String getCommand() {
		return command;
	}

	public String[] getParameter() {
		return parameter;
	}

	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}

	public DeclaredCommand getDeclaredCommand() {
		return declaredCommand;
	}

	public void setDeclaredCommand(DeclaredCommand declaredCommand) {
		this.declaredCommand = declaredCommand;
	}
	
	public String[] getSource(){
		return this.source;
	}
	
	@Override
	public String toString() {
		return id+":"+Arrays.toString(source);
	}
}
