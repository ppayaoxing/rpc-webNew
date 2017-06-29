package com.rpc.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.rpc.spring.config.register.RegisterServer;
import com.rpc.util.ObjectCacheUtils;

public class RpcData {

	private Method method;
	private Object[] args;
	private Class<?> clazz;
//	private String url;
//	private List<String> urls = null;
	private int timeout;
	private String interfaceClass; 
	private String group;
	
	public RpcData(Method method, Object[] args, Class<?> clazz, int timeout,String interfaceClass,String group) {
		super();
		this.method = method;
		this.args = args;
		this.clazz = clazz;
//		this.url = url;
		this.timeout = timeout;
		this.interfaceClass = interfaceClass;
		this.group = group;
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
		if(ObjectCacheUtils.getUrls(group).size() > 0)
			return ObjectCacheUtils.getUrls(group);
		List<String> urls = new ArrayList<String>();
		String urlStr = RegisterServer.getRegisterServer().getUrls(group);
		if(urlStr.indexOf(";") != -1){
			String[] strs = urlStr.split(";");
			for(String temp : strs){
				if(temp == null || "".equals(temp)){
					continue;
				}
				urls.add(getUrl(temp));
			}
		}else{
			urls.add(getUrl(urlStr));
		}
		ObjectCacheUtils.addUrls(group, urls);
		return urls;
	}


	private String getUrl(String temp) {
		if(temp.endsWith("/")){
			temp += interfaceClass;
		}else{
			temp += "/" + interfaceClass;
		}
		return temp;
	}
	
	public String getClusterKey(){
		return clazz.getName()+"_"+method.getName()+"."+ObjectCacheUtils.getParameterNames(getParameterType());
	}
	
}
