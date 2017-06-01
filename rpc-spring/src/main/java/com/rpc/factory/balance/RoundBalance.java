package com.rpc.factory.balance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rpc.factory.RpcData;

/**
 * 轮询 
 * @author Administrator
 */
public class RoundBalance implements Balance {
	
	private ConcurrentHashMap<String, AtomicInteger> sequences = new ConcurrentHashMap();

	public String getUrl(RpcData rpcData){
		List<String> urls = rpcData.getUrls();
		String key = rpcData.getClusterKey();
		int length = urls.size();
		AtomicInteger atomicNum = sequences.get(key);
		if(atomicNum==null){
			atomicNum = new AtomicInteger(0);
			sequences.put(key, atomicNum);
		}
		return urls.get(atomicNum.getAndIncrement()%length);
	}

}
