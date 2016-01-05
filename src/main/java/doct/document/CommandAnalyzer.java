package doct.document;

import java.util.List;

/**
 * 命令行分析器接口
 * @author wei
 */
public interface CommandAnalyzer {
	
	/**
	 * 从命令行中提取命令并分析
	 * @param lineText
	 * @return
	 */
	public List<CommandDescriptor> analyze(String line);
	
	/**
	 * 获取所有注册的命令
	 * @return
	 */
	public abstract List<DeclaredCommand> getCommands();
}
