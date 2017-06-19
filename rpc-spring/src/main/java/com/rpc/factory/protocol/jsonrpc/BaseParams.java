package com.rpc.factory.protocol.jsonrpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.yzmy.jsonrpc4j.JsonRpcMethod;
import com.yzmy.jsonrpc4j.JsonRpcParam;

/**
 * 请求参数
 */
public class BaseParams {
    private String jsonrpc="2.0";
    private String method;
    private Object params;
    private int id=-2339;

    public BaseParams() {
		super();
	}

	public BaseParams(String command, Object params) {
        this.method = command;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }


	public String getMethod() {
		return method;
	}

	public Object getParams() {
        return params;
    }

    public int getId() {
        return id;
    }
    
    public BaseParams build(Class<?> clazz,Method method,Object[] args){
    	JsonRpcMethod rpcMethod = method.getAnnotation(JsonRpcMethod.class);
    	this.method = clazz.getSimpleName()+"."+rpcMethod.value();
    	Annotation[][] annotations = method.getParameterAnnotations();
    	Map<String,Object> paramMaps = new HashMap<String,Object>();
    	int index = 0;
    	for(Annotation[] annotation:annotations){
    		JsonRpcParam param =(JsonRpcParam)annotation[0];
    		paramMaps.put(param.value(), args[index]);
    		index++;
    	}
    	this.params = paramMaps;
    	return this;
    }
}
