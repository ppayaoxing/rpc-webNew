package com.rpc.factory.cluster;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.rpc.factory.ParamsData;
import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.protocol.Protocol;

/**
 * 异步请求
 * 
 * @author Administrator
 */
public class AsyncCluster implements Cluster {

	private static ThreadLocal<Future<Object>> local = new ThreadLocal<Future<Object>>();

	@Override
	public Object cluster(final Balance balance, final Protocol protocol, final RpcData rpcData) throws Exception {
		ExecutorService pool = Executors.newCachedThreadPool();
		try {
			Future<Object> future = pool.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					try {
						String url = balance.getUrl(rpcData);
						Object proxy = protocol.refer(rpcData.getClazz(), url,
								new ParamsData(rpcData.getClazz().getName(), rpcData.getMethodName(),
										rpcData.getTimeout(), rpcData.getClusterKey(), rpcData.getParameterType(),
										rpcData.getArgs()));
						Method method = proxy.getClass().getMethod(rpcData.getMethodName(), rpcData.getParameterType());
						return method.invoke(proxy, rpcData.getArgs());
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			local.set(future);
		} finally {
			pool.shutdown();
		}
		return null;
	}

}
