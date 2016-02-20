package doct.document.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import doct.document.test.model.Address;
import doct.document.test.model.User;
import doct.document.xls.XlsDocumentRenderExecutor;
import junit.framework.TestCase;

public class XlsRenderTest extends TestCase{
	
	@Test
	public void testRenderXls() throws Exception{
		String output = "D:/test.xls";
		InputStream ins = this.getClass().getResourceAsStream("/template.xls");
		
//		FileInputStream ins = new FileInputStream(new File("E:/userinfo.xls"));
		HSSFWorkbook workbook =  new HSSFWorkbook(ins);
		
		XlsDocumentRenderExecutor renderExecutor = new XlsDocumentRenderExecutor(workbook);
		
		List<User> list = new ArrayList<User>();
		User user = new User("张山",28,new Date(),"1234567891");
		Address adr = new Address("123456", "北京市昌平区");
		user.setAddress(adr);
		list.add(user);
		
		User user2 = new User("李四",10,new Date(),"1234560980");
		Address adr2 = new Address("333456", "北京市海淀区");
		user2.setAddress(adr2);
		list.add(user2);
		
		User user3 = new User("王五",18,new Date(),"1234560999");
		Address adr3 = new Address("0998876", "北京市东城区");
		user3.setAddress(adr3);
		list.add(user3);
		
		Map model = new HashMap();
		model.put("user", user);
		model.put("list", list);
		
		Map m = new HashMap();
		m.put(1, "你好");
		
		model.put("map", m);
		
		renderExecutor.renderDocument(model);
		
		FileOutputStream out = new FileOutputStream(new File(output));
		workbook.write(out);
		out.close();
	}
	
}
