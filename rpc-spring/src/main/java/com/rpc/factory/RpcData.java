package com.rpc.factory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.rpc.util.ObjectCacheUtils;

public class RpcData {

	private Method method;
	private Object[] args;
	private Class<?> clazz;
	private String url;
	private List<String> urls = null;
	private int timeout;
	
	public RpcData(Method method, Object[] args, Class<?> clazz, String url,int timeout) {
		super();
		this.method = method;
		this.args = args;
		this.clazz = clazz;
		this.url = url;
		this.timeout = timeout;
	}

	
	public int getTimeout() {
		return timeout;
	}


	public String getMethodName() {
		return this.method.getName();
	}

	public Class<?>[] getParameterType(){
		return this.method.getParameterTypes();
	}
	
	public Object[] getArgs() {
		return this.args;
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	public List<String> getUrls() {
		if(urls!=null)
			return urls;
		if(this.url.indexOf(";") != -1){
			return Arrays.asList(this.url.split(";"));
		}
		return Arrays.asList(new String[]{this.url});
	}
	
	
	
	public String getClusterKey(){
		return clazz.getName()+"_"+method.getName()+"."+ObjectCacheUtils.getParameterNames(getParameterType());
	}
	
}
