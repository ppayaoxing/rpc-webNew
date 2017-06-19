package com.test.rpc.impl;

import com.rpc.annotation.ReadOnly;
import com.test.rpc.User;
import com.test.rpc.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser(String name) {
		System.out.println("addUser = "+name);
	}

	@ReadOnly
	@Override
	public String getName() {
		return "hello world";
	}

	@ReadOnly
	@Override
	public User getUser(int id,User user) {
		User users = new User();
		users.setId(id);
		return users;
	}
	

}
