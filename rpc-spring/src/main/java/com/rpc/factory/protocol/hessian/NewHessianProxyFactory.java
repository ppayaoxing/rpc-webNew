package com.rpc.factory.protocol.hessian;

import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;

public class NewHessianProxyFactory {

	public static HessianProxyFactory getHessianProxyFactory(Map<String,String> params){
		HessianProxyFactory proxy = new HessianProxyFactory();
		ParamsUrlHessianConnectionFactory paramsConnection = new ParamsUrlHessianConnectionFactory(params);
		proxy.setConnectionFactory(paramsConnection);
		paramsConnection.setHessianProxyFactory(proxy);
		return proxy;
	}
}
