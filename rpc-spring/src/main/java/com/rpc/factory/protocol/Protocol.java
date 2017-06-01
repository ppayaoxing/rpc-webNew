package com.rpc.factory.protocol;

public interface Protocol {

	public <T> T refer(Class<T> clazz,String url) throws Exception;
	
}
