package com.rpc.factory.protocol;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianProtocol implements Protocol{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T refer(Class<T> clazz, String url) throws Exception {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
      hessianProxyFactory.setConnectTimeout(10000);
      hessianProxyFactory.setReadTimeout(10000);
       return (T) hessianProxyFactory.create(clazz, url, Thread.currentThread().getContextClassLoader());
	}

}
