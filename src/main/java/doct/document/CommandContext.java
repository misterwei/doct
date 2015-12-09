package doct.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandContext implements Map<String,Object>{
	public static final CommandContext NONE = new CommandContext(null, -1, "", null);
	
	private CommandLine line;
	private Integer lineIndex;
	private int index;
	private String name;
	private String endName;
	private String[] descriptor;
	
	private String nextLineId;
	private Integer nextLineIndex;
	private Integer nextIndex;
	
	private String nextCommand;
	
	private Map<String,Object> _values = new HashMap<String,Object>();
	
	public CommandContext(CommandLine line,int index, String name, String endName) {
		this.line = line;
		this.index = index;
		this.name = name;
		this.endName = endName;
	}
	
	public int getLineIndex() {
		return lineIndex;
	}
	
	public void setLineIndex(Integer lineIndex) {
		this.lineIndex = lineIndex;
	}

	public String getNextLineId() {
		return nextLineId;
	}

	/**
	 * 设置跳转id后，lineIndex将置0
	 * @param nextLineId
	 */
	public void setNextLineId(String nextLineId) {
		this.setNextLineIndex(0);
		this.nextLineId = nextLineId;
	}
	
	public Integer getNextLineIndex() {
		return nextLineIndex;
	}

	public Integer getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(Integer nextIndex) {
		this.nextIndex = nextIndex;
	}

	
	public String getNextCommand() {
		return nextCommand;
	}

	/**
	 * 下个要执行的命令，可以是正则表达式
	 * @param nextCommand
	 */
	public void setNextCommand(String nextCommand) {
		this.nextCommand = nextCommand;
	}

	/**
	 * 设置nextLineIndex可以进行快速定位
	 * @param lineIndex
	 */
	public void setNextLineIndex(Integer nextLineIndex) {
		this.nextLineIndex = nextLineIndex;
	}

	public CommandLine getCommandLine() {
		return line;
	}

	public int getIndex() {
		return index;
	}
	public String getName() {
		return name;
	}
	
	public String getEndName() {
		return endName;
	}

	public String[] getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String[] descriptor) {
		this.descriptor = descriptor;
	}

	public boolean isHasJump(){
		return getNextCommand() != null || getNextIndex() != null || isHasLineJump();
	}
	
	public boolean isHasLineJump(){
		return getNextLineId() != null || getNextLineIndex() != null;
	}
	
	public void clearLineJump(){
		setNextLineId(null);
		setNextLineIndex(null);
	}
	
	public void clearAllJump(){
		clearLineJump();
		setNextIndex(null);
		setNextCommand(null);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((lineIndex == null) ? 0 : lineIndex.hashCode());
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
		CommandContext other = (CommandContext) obj;
		if (index != other.index)
			return false;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		if (lineIndex == null) {
			if (other.lineIndex != null)
				return false;
		} else if (!lineIndex.equals(other.lineIndex))
			return false;
		return true;
	}

	public int size() {
		return _values.size();
	}

	public boolean isEmpty() {
		return _values.isEmpty();
	}

	public boolean containsKey(Object key) {
		return _values.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return _values.containsValue(value);
	}

	public Object get(Object key) {
		return _values.get(key);
	}

	public Object put(String key, Object value) {
		return _values.put(key, value);
	}

	public Object remove(Object key) {
		return _values.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		_values.putAll(m);
	}

	public void clear() {
		clearAllJump();
		_values.clear();
	}

	public Set<String> keySet() {
		return _values.keySet();
	}

	public Collection<Object> values() {
		return _values.values();
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return _values.entrySet();
	}

	
}