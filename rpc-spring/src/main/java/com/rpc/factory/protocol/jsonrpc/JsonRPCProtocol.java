package com.rpc.factory.protocol.jsonrpc;

import com.rpc.factory.ParamsData;
import com.rpc.factory.protocol.Protocol;

public class JsonRPCProtocol implements Protocol{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T refer(Class<T> clazz, String url, ParamsData params) throws Exception {
		JsonRPCFactory jsonRPCFactory = new JsonRPCFactory(params);
		return (T) jsonRPCFactory.create(clazz, url, Thread.currentThread().getContextClassLoader());
	}

}
