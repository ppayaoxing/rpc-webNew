package com.rpc.factory;

import com.rpc.factory.handler.Handler;
import com.rpc.factory.proxy.JdkProxy;

/**
 * 代理工厂
 * @author Administrator
 *
 */
public class ProxyFactory {
	
	private ProxyFactory(){}
	
	private static class SingleFactory{
		private final static ProxyFactory instance = new ProxyFactory();
	}
	
	public static ProxyFactory  getProxyFactory(){
		return SingleFactory.instance;
	}
	public JdkProxy getProxy(Handler handler){
		return new JdkProxy(handler);
	}	

}
