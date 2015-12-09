package doct.document;

import java.util.List;

/**
 * 命令行分析器接口
 * @author wei
 */
public interface CommandLineAnalyzer {
	
	/**
	 * 使用提供的命令分析器解析出完整命令行
	 * @param lineText
	 * @return
	 */
	public List<CommandLine> analyze();
	
	/**
	 * 在分析命令前会先设置CommandAnalyzer
	 * @param analyzer
	 */
	public void setCommandAnalyzer(CommandAnalyzer analyzer);
}
