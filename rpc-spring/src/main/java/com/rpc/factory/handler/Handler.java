package com.rpc.factory.handler;

import java.lang.reflect.Method;

public interface Handler {

	public Object exec(Method method, Object[] args) throws Exception;
	
}
