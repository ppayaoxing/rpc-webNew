package com.rpc.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.rpc.util.ObjectCacheUtils;

public class RpcData {

	private Method method;
	private Object[] args;
	private Class<?> clazz;
	private String url;
	private List<String> urls = null;
	private int timeout;
	private String interfaceClass; 
	
	public RpcData(Method method, Object[] args, Class<?> clazz, String url,int timeout,String interfaceClass) {
		super();
		this.method = method;
		this.args = args;
		this.clazz = clazz;
		this.url = url;
		this.timeout = timeout;
		this.interfaceClass = interfaceClass;
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
		if(this.urls != null)
			return this.urls;
		this.urls = new ArrayList<String>();
		if(this.url.indexOf(";") != -1){
			String[] strs = this.url.split(";");
			for(String str : strs){
				this.urls.add(str + interfaceClass);
			}
		}else{
			this.urls.add(url + interfaceClass);
		}
		return this.urls;
	}
	
	public String getClusterKey(){
		return clazz.getName()+"_"+method.getName()+"."+ObjectCacheUtils.getParameterNames(getParameterType());
	}
	
}
