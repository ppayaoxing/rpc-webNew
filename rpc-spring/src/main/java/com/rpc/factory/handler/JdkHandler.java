package com.rpc.factory.handler;

import java.lang.reflect.Method;

import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.cluster.Cluster;
import com.rpc.factory.protocol.Protocol;

/**
 */
public class JdkHandler implements Handler {

	private Cluster cluster;//集群
	private Protocol protocol;//协议
	private Balance balance;//负载均衡
	private Class<?> clazz;//代理对象字节
	private int timeout;
	private String interfaceClass; 
	private String group;
	
	public JdkHandler(Cluster cluster, Protocol protocol, Class<?> clazz, Balance balance,int timeout,String interfaceClass,String group) {
		super();
		this.cluster = cluster;
		this.protocol = protocol;
		this.clazz = clazz;
		this.balance = balance;
		this.timeout = timeout;
		this.interfaceClass = interfaceClass;
		this.group =  group;
	}

	@Override
	public Object exec(Method method, Object[] args) throws Exception {
		return cluster.cluster(balance,protocol, new RpcData(method, args,clazz, this.timeout,this.interfaceClass,this.group));
	}

}
