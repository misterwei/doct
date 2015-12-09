package doct.document;

import java.util.List;

import ognl.OgnlContext;

/**
 * 子类需实现无状态
 * @author wei
 */
public interface DeclaredCommand {
	public static final Object NO_OUTPUT = new Object();
	
	/**
	 * 执行命令
	 * @param ctx 上下文，模型中的值及环境中的值都可以在这里找到
	 * @param cmdCtx 当前命令的上下文
	 * @param prevCmdCtx 上一个命令的上下文，主要用来做 else 操作，可以用来做跳转操作
	 * @param params 文档传参
	 * @return
	 * @throws Exception
	 */
	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx,CommandContext prevCmdCtx, Object ...params) throws Exception;
	
	/**
	 * 执行命令结尾
	 * @param ctx
	 * @param params
	 * @return 返回true表示CommandContext出栈，执行之后的命令
	 */
	public boolean doEnd(OgnlContext ctx, CommandContext cmdCtx, Object ...params) throws Exception;
	
	/**
	 * 命令名称
	 * @return
	 */
	public String getStartName();
	
	/**
	 * 命令结尾，没有则返回NULL
	 * @return
	 */
	public String getEndName();
	
	/**
	 * 解析命令
	 * @param parts
	 * @return
	 */
	public List<String[]> analyze(String[] parts);
	
}
