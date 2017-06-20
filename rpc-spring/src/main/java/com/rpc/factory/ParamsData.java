package com.rpc.factory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ParamsData implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String clazz;//接口
	private String methodName;
	private int timeout;
	private String clusterKey;
	private Class<?>[] parameterTypes;
	private Object[] values;
	
	public ParamsData(String clazz, String methodName, int timeout,String clusterKey,Class<?>[] parameterTypes,Object[] values) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
		this.timeout = timeout;
		this.clusterKey = clusterKey;
		this.parameterTypes = parameterTypes;
		this.values = values;
	}
	public String getClazz() {
		return clazz;
	}
	public String getMethodName() {
		return methodName;
	}
	public int getTimeout() {
		return timeout;
	}
	
	public String getClusterKey() {
		return clusterKey;
	}
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setClusterKey(String clusterKey) {
		this.clusterKey = clusterKey;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public Map<String,String> getHeaderParams(){
		Map<String,String> params = new HashMap<String,String>();
		params.put("methodName",this.methodName);
		params.put("clazz", this.clazz);
		params.put("clusterKey", this.clusterKey);
		return params;
	}
}
