package doct.document.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import doct.document.CommandContext;
import doct.document.CommandDescriptor;
import doct.document.DeclaredCommand;
import doct.document.DocumentUtils;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * {%for item in list%}{%endfor%}
 * {%for item in list use #index%}{%endfor%}
 * @author wei
 *
 */
public class ForCommand implements DeclaredCommand{

	public Object doCommand(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		String[] cmdparts = cmdCtx.getDescriptor();
		//for item in list
		if(cmdparts.length == 4){
			//设置数组索引值+1
			String prefix = cmdparts[1].replace("#", "");
			String index_name = prefix+"_index";
			ctx.put(index_name, 0);
		}
		
		//如果集合size为0，则直接跳到endfor
		String part = cmdparts[3];
		Object obj = Ognl.getValue(part, ctx, ctx.getRoot());
		int size = getSize(obj);
		if(size == 0){
			cmdCtx.setNextCommand(DocumentUtils.getEndName(this));
		}
		
		return NO_OUTPUT;
	}

	/**
	 * 返回true表示for结束
	 */
	public boolean doEnd(OgnlContext ctx, CommandContext cmdCtx, Object... params) throws Exception {
		String[] cmdparts = cmdCtx.getDescriptor();
		
		Boolean breaked = (Boolean)ctx.get("__break__");
		if(breaked != null && breaked){
			ctx.remove("__break__");
			return true;
		}
		
		Boolean continued = (Boolean)ctx.get("__continue__");
		if(continued != null && continued){
			ctx.remove("__continue__");
		}
		
		int __index = 0;
		String index_name = null;
		//for item in list
		if(cmdparts.length == 4){
			//设置数组索引值+1
			String prefix = cmdparts[1].replace("#", "");
			index_name = prefix+"_index";
			__index = (Integer)ctx.get(index_name); 
			index_name = "#" + index_name;
		}else{
			//for item in list use index
			index_name = cmdparts[5];
			__index = (Integer)Ognl.getValue(cmdparts[5], ctx, ctx.getRoot()); 
		}
		
		__index++;
		Ognl.setValue(index_name, ctx, ctx.getRoot(), __index);
		
		//判断索引值范围
		//for val in list
		String part = cmdparts[3];
		Object obj = Ognl.getValue(part, ctx, ctx.getRoot());
		int size = getSize(obj);
		
		if(__index < size){
			//跳转继续执行
			int lineIndex = cmdCtx.getLineIndex();
			int cmdIndex = cmdCtx.getIndex();
			
			cmdCtx.setNextLineIndex(lineIndex);
			cmdCtx.setNextIndex(cmdIndex+1);
			return false;
		}
		return true;
	}
	
	private int getSize(Object obj){
		int size = 0;
		if(obj.getClass().isArray()){
			Object[] objs = (Object[])obj;
			size = objs.length;
		}else{
			Collection coll = (Collection)obj;
			size = coll.size();
		}
		return size;
	}
	
	public String getStartName() {
		return "for";
	}

	public boolean isHasEnd() {
		return true;
	}

	public List<CommandDescriptor> analyze(String[] parts) {
		List<CommandDescriptor> descriptors = new ArrayList<CommandDescriptor>();
		if(parts.length > 1){
			String[] subparts = parts[1].split("\\s+");
			String[] newparts = new String[subparts.length + 1];
			newparts[0] = parts[0];
			for(int i=0;i<subparts.length;i++){
				newparts[i+1] = subparts[i];
			}
			descriptors.add(new CommandDescriptor(newparts));
			parts = newparts;
		}else{
			descriptors.add(new CommandDescriptor(parts));
		}
		if(!parts[0].equals(getStartName()))
			return descriptors;
		//for val in list
		//设置 val = list[#__index__]
		String prefix = parts[1].replace("#", "");
		String[] newcmd = new String[]{"set", parts[1], parts[3]+"[#"+prefix+"_index]"};
		descriptors.add(new CommandDescriptor(newcmd));
		return descriptors;
	}

}
