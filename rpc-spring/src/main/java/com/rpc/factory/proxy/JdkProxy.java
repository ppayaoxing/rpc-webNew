package com.rpc.factory.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.rpc.factory.handler.Handler;

public class JdkProxy implements InvocationHandler{
	
	private Handler handler;
	
	public JdkProxy(Handler handler){
		this.handler = handler;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return handler.exec(method,args);
	}

}
