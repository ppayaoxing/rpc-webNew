package com.rpc.factory.cluster;

import java.lang.reflect.Method;

import com.rpc.factory.ParamsData;
import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.protocol.Protocol;

/**
 * 失败重试 
 * @author Administrator
 */
public class FailOverCluster implements Cluster {

	private static final int failNum = 3;
	
	@Override
	public Object cluster(Balance balance,Protocol protocol,RpcData rpcData) throws Exception {
			for(int i =0;i<failNum;i++){
				try{
				 Class<?> clazz = rpcData.getClazz();
				 String url = balance.getUrl(rpcData);
				 Object proxy = protocol.refer(clazz,url
						 ,new ParamsData(rpcData.getClazz().getName(),rpcData.getMethodName(),rpcData.getTimeout(),rpcData.getClusterKey(),rpcData.getParameterType()));
				 Method method = proxy.getClass().getMethod(rpcData.getMethodName(), rpcData.getParameterType());
	             return method.invoke(proxy, rpcData.getArgs());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		throw new Exception(rpcData.getClazz()+"超过重试次数");
	}

}
