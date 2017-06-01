//package com.xw.test.rpcData;
//
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Test;
//
//import com.xw.base.TestBase;
//import com.xw.common.Pagination;
//import com.xw.model.RpcData;
//import com.xw.server.RpcDataService;
//public class RpcDataTest extends TestBase {
//	

//	@Resource
//	private RpcDataService rpcDataService;
//	
//	private final static ObjectMapper mapper = new ObjectMapper();
//	
//	@Test
//	public void testSave(){
//		RpcData rpcData = new RpcData();
//		rpcData.setClassname("classname");
//		rpcData.setFullclassname("fullClassname");
//		rpcData.setUrl("url");
//		rpcData.setTime(new Date());
//		int id = rpcDataService.save(rpcData);
//		System.out.println(id);
//	}
//	
//	@Test
//	public void testList(){
//		Pagination<RpcData> page = rpcDataService.list(1,10);
//		try {
//			System.out.println("page:"+mapper.writeValueAsString(page));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//}
