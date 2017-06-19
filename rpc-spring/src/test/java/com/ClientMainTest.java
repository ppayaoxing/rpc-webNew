package com;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.rpc.User;
import com.test.rpc.UserService;

public class ClientMainTest {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:appliactionContextClient.xml");
		UserService userService = (UserService)context.getBean("com.test.rpc.UserService");
//		userService.addUser("jaco");
//		System.out.println(userService.getName());
		System.out.println(userService.getUser(10,new User()));
//		userService.addUser("jaco");
//		userService.addUser("jaco");
//		userService.addUser("jaco");
//		userService.addUser("jaco");
	}

}
