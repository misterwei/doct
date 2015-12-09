package doct.document;

import ognl.OgnlContext;

/**
 * 文本快渲染接口
 * @author wei
 */
public interface TextBlockRender {
	
	public void render(OgnlContext ctx, TextBlockInfo block) throws Exception;
	
}
