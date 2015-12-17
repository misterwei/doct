package doct.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ognl.OgnlContext;

public abstract class DocumentRenderExecutor {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 渲染引擎
	 * @param model
	 * @throws Exception
	 */
	public void renderDocument(Map<String, Object> model) throws Exception{
		List<CommandLine> lines = getCommandLines();
		
		//上下文
		OgnlContext context = new OgnlContext();
		
		//清除命令信息
		clear(context, lines);
		
		//命令栈，只有end标签的才会加入命令栈
		Stack<CommandContext> commandStack = new Stack<CommandContext>();
		
		//记录栈大小
		int stack_size = 0;
		
		//命令跳转使用
		CommandContext jumpCommand = null;
		//上一个命令
		CommandContext prevCommand = CommandContext.NONE;
		
		Map<String,Object> root = model;
		if(root == null){
			root = new HashMap<String,Object>();
		}
		context.setRoot(root);
		
		int lineIndex = -1;
		int cmdIndex = -1;
		while(lineIndex < lines.size() - 1 ){
			lineIndex++;
			CommandLine cmdline = lines.get(lineIndex);
			
			//检测是否有命令行跳转
			if(jumpCommand != null && jumpCommand.isHasLineJump()){
				if(jumpCommand.getNextLineId() != null && !jumpCommand.getNextLineId().equals(cmdline.getId())){
					if(lineIndex >= lines.size())
						throw new Exception("没有找到命令行Id " + jumpCommand.getNextLineId());
					continue;
				}else if(jumpCommand.getNextLineIndex() != null && jumpCommand.getNextLineIndex() != lineIndex){
					if(lineIndex >= lines.size())
						throw new Exception("命令行索引超出了命令行数");
					continue;
				}
				jumpCommand.clearLineJump();
			}
			
			TextBlockInfo blockInfo = cmdline.getTextInfo();
			try{
				List<String[]> cmds = cmdline.getDescriptors();
				while(cmdIndex < cmds.size() - 1){
					cmdIndex ++;
					String[] descr = cmds.get(cmdIndex);
					String cmdName = descr[0];
					
					//提取匹配的Command
					List<DeclaredCommand> declaredCmds = getDeclaredCommands();
					
					//检测是否有命令跳转
					if(jumpCommand != null && jumpCommand.isHasJump()){
						if(jumpCommand.getNextCommand() != null ){
							
							if(!cmdName.matches(jumpCommand.getNextCommand())){
								if(!cmdName.startsWith("end")){
									/**
									 * 如果略过的命令中有结尾命令则，入栈
									 */
									for(DeclaredCommand decmd : declaredCmds){
										if(cmdName.equals(decmd.getStartName())){
											if(decmd.getEndName() != null){
												CommandContext cmdCtx = new CommandContext(cmdline, cmdIndex, cmdName, decmd.getEndName());
												cmdCtx.setDescriptor(descr);
												cmdCtx.setLineIndex(lineIndex);
												commandStack.push(cmdCtx);
											}
											break;
										}
									}
								}else {
									/**
									 * 如果栈尾的end命令与当前相等，则对当前命令出栈，处理一下情况
									 * for a in list
									 *   if aa
									 *   	break
									 *   endif
									 * endfor
									 * 当break for循环时，先对if出栈
									 */
									CommandContext cmdCtx  = commandStack.peek();
									if(cmdName.equals(cmdCtx.getEndName())){
										commandStack.pop();
									}
								}
								continue;
							}
							
							//对比栈大小
							if(commandStack.size() > stack_size){
								CommandContext cmdCtx  = commandStack.peek();
								if(cmdName.equals(cmdCtx.getEndName())){
									commandStack.pop();
								}
								continue;
							}
							
						}else if(jumpCommand.getNextIndex() != null && cmdIndex != jumpCommand.getNextIndex()){
							if(cmdIndex >= cmds.size() - 1)
								throw new Exception("跳转命令索引超出了命令数");
							continue;
						}
						
						jumpCommand.setNextCommand(null);
						jumpCommand.setNextIndex(null);
					}
					//跳转命令置空
					jumpCommand = null;
					for(DeclaredCommand decmd : declaredCmds){
						if(cmdName.equals(decmd.getStartName())){
							CommandContext cmdCtx = new CommandContext(cmdline, cmdIndex, cmdName, decmd.getEndName());
							cmdCtx.setDescriptor(descr);
							cmdCtx.setLineIndex(lineIndex);
							if(decmd.getEndName() != null)
								commandStack.push(cmdCtx);
							Object temp = doCommand(decmd, context, cmdCtx, prevCommand);
							if(cmdCtx.isHasJump()){
								jumpCommand = cmdCtx;
								prevCommand.clearAllJump();
							}else if(prevCommand.isHasJump()){
								//使用前一个命令进行跳转
								jumpCommand = prevCommand;
							}
							if(temp != DeclaredCommand.NO_OUTPUT){
								storeLineOutValue(cmdline.getId(), context, temp);
								render(context, blockInfo);
							}
							//设置为前一个命令
							prevCommand = cmdCtx;
							
						}else if(cmdName.equals(decmd.getEndName())){
							CommandContext cmdCtx = commandStack.peek();
							boolean pop = doEnd(decmd, context, cmdCtx);
							if(pop){
								commandStack.pop();
							}else{
								if(cmdCtx.isHasJump()){
									jumpCommand = cmdCtx;
								}
							}
						}
					}
					if(jumpCommand != null && jumpCommand.isHasJump()){
						//如果指定了下一个命令，则直接向后寻找命令
						if(jumpCommand.getNextCommand() != null){
							stack_size = commandStack.size();
							jumpCommand.clearLineJump();
							jumpCommand.setNextIndex(null);
							continue;
						}else if(jumpCommand.isHasLineJump()){
							//重置命令行索引
							lineIndex = -1;
							if(jumpCommand.getNextLineIndex() != null && jumpCommand.getNextLineIndex() >= lines.size()){
								throw new Exception("命令行索引超出了命令行数");
							}
							break;
						}else{
							//重置cmdIndex,本行内重新循环
							cmdIndex = -1;
						}
					}
				}
				
			}catch(Exception e){
				log.error("exec command line exception ",e );
			}
			
			//重置cmdIndex
			cmdIndex = -1;
		}
		
		if(!commandStack.isEmpty()){
			CommandContext cmdCtx = commandStack.peek();
			if(cmdCtx.getNextCommand() != null){
				throw new Exception("命令行已经全部运行结束，但是没有找到这个命令 "+ cmdCtx.getNextCommand());
			}else{
				throw new Exception("命令行已经全部运行结束，但是命令栈不是空的");
			}
		}
		
		
	}
	
	/**
	 * 清空指令所在块
	 * @throws Exception
	 */
	protected void clear(OgnlContext ctx, List<CommandLine> lines) throws Exception{
		int lineIndex = -1;
		TextBlockInfo prev = null;
		if(lines.isEmpty())
			return;
		while(lineIndex < lines.size() - 1 ){
			lineIndex++;
			CommandLine cmdline = lines.get(lineIndex);
			TextBlockInfo cur = cmdline.getTextInfo();
			if(prev == null )
				prev = cur;
			if(prev != cur){
				render(ctx, prev);
				prev = cur;
			}
		}
		
		render(ctx, prev);
	}
	
	
	protected Object doCommand(DeclaredCommand cmd, OgnlContext context, CommandContext cmdCtx, CommandContext prevCmdCtx) throws Exception{
		return cmd.doCommand(context, cmdCtx, prevCmdCtx);
	}
	
	protected boolean doEnd(DeclaredCommand cmd, OgnlContext context, CommandContext cmdCtx) throws Exception{
		return cmd.doEnd(context, cmdCtx);
	}
	
	protected void storeLineOutValue(String lineId, OgnlContext context, Object value) throws Exception{
		context.put("_"+lineId, value);
	}
	
	protected void render(OgnlContext ctx, TextBlockInfo block) throws Exception{
		getTextBlockRender().render(ctx, block);
	}
	
	protected abstract TextBlockRender getTextBlockRender();
	
	protected abstract List<CommandLine> getCommandLines();
	
	protected abstract List<DeclaredCommand> getDeclaredCommands();
}
