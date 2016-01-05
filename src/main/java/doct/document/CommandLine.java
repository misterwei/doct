package doct.document;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令行信息
 * @author wei
 */
public class CommandLine{
	private String source;
	private String id;
	private int start;
	private int end;
	private List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
	private TextBlockInfo textInfo;
	
	public CommandLine(String id,TextBlockInfo textInfo, int start, int end){
		this.start = start;
		this.end = end;
		this.id = id;
		this.textInfo = textInfo;
	}
	
	public void addDescriptor(CommandDescriptor part){
		descriptors.add(part);
	}
	
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public List<CommandDescriptor> getDescriptors(){
		return descriptors;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String commandLine) {
		this.source = commandLine;
	}

	public String getId() {
		return id;
	}
	
	public TextBlockInfo getTextInfo() {
		return textInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandLine other = (CommandLine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}