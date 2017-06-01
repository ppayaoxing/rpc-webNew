package com.xw;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.test.rpc.UserService;

public class HelloHessianClient {

	
	public static String urlName = "http://127.0.0.1/rpc-server/com.test.rpc.UserService";

	public static void main(String[] args) throws MalformedURLException {

		HessianProxyFactory factory = new HessianProxyFactory();
		// 开启方法重载
		factory.setOverloadEnabled(true);

		UserService userService = (UserService) factory.create(
				UserService.class, urlName);

		// 调用方法
		userService.addUser("jaco");
	}
}
