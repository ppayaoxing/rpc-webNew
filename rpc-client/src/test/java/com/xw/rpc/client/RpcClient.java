//package com.xw.rpc.client;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.xw.base.TestBase;
//import com.xw.common.Pagination;
//import com.xw.model.RpcData;
//import com.xw.server.RpcDataService;
//
//public class RpcClient extends TestBase {
//	
//	@Autowired
//	private RpcDataService rpcDataService;
//	
//	private final static ObjectMapper mapper = new ObjectMapper();
//	
//	@Test
//	public void testClient(){
//		Pagination<RpcData> page = rpcDataService.list(1, 10);
//		try {
//			System.out.println(mapper.writeValueAsString(page));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
