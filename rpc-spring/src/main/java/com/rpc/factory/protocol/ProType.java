package com.rpc.factory.protocol;

import com.rpc.factory.protocol.hessian.HessianProtocol;
import com.rpc.factory.protocol.http.HttpProtocol;
import com.rpc.factory.protocol.jsonrpc.JsonRPCProtocol;

public enum ProType {

	HESSIAN("hessian",HessianProtocol.class),JSONRPC("jsonRPC",JsonRPCProtocol.class),HTTP("http",HttpProtocol.class);
	
	private String value;
	private Class<? extends Protocol> obj;
	
	ProType(String value, Class<? extends Protocol> obj) {
		this.value = value;
		this.obj = obj;
	}

	public String getValue() {
		return value;
	}

	public Protocol getObj() throws InstantiationException, IllegalAccessException {
		return (Protocol) obj.newInstance();
	}

	public static ProType getProType(String value){
		switch(value){
		case "hessian":return HESSIAN;
		case "jsonRPC":return JSONRPC;
		case "http":return HTTP;
		default : return HESSIAN;
		}
	}
	
}
