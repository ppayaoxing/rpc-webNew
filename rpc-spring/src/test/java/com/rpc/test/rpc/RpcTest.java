package com.rpc.test.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpc.spring.config.tag.RpcConfigServer;
import com.rpc.test.TestBase;

public class RpcTest extends TestBase {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:appliactionContext.xml");
		try{
			RpcConfigServer<Object> RpcConfig = (RpcConfigServer<Object>) context.getBean("com.prc.user.UserService");
			System.out.println(RpcConfig);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
