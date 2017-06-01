package com.test.rpc.impl;

import com.test.rpc.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser(String name) {
		System.out.println("addUser = "+name);
	}

}
