package com;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.rpc.UserService;

public class ClientMainTest {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:appliactionContextClient.xml");
		UserService userService = (UserService)context.getBean("com.test.rpc.UserService");
		userService.addUser("jaco");
		userService.addUser("jaco");
		userService.addUser("jaco");
		userService.addUser("jaco");
		userService.addUser("jaco");
	}

}
