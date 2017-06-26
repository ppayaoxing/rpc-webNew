package com.admin.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.admin.common.ServerInfoData;

public class AdminCacheUtils {
	
	//接口和实现类的映射
	private static ConcurrentHashMap<String, Class<?>> interfaceToClass = new ConcurrentHashMap<>();
	
	//key:ip:port/webApp  value:最近一千条心跳数据
	private static ConcurrentHashMap<String, ServerInfoData> serverList = new ConcurrentHashMap<>();
	
	// group --> urls(获取时 多个URL按;分割)
	private static ConcurrentHashMap<String, Set<String>> urls = new ConcurrentHashMap<String, Set<String>>();
	
	public static void addInterfaceCalssMap(String interfaceClass,Class<?> clazz){
		interfaceToClass.put(interfaceClass, clazz);
	}
	
	public static Class<?> getClazz(String interfaceClass){
		if(interfaceToClass.containsKey(interfaceClass))
			return interfaceToClass.get(interfaceClass);
		return null;
	}
	
	public static void addServer(ServerInfoData serverInfoData){
		if(!serverList.containsKey(serverInfoData.getId())){
			serverList.put(serverInfoData.getId(), serverInfoData);
		}
	}
	public static ServerInfoData getServerInfo(String serverId){
		if(serverList.containsKey(serverId)){
			return serverList.get(serverId);
		}
		return null;
	}
	
	public static void addUrl(String group ,String url){
		Set<String> urlSet = new HashSet<>();
		if(urls.containsKey(group)){
			urlSet = urls.get(group);
		}
		urlSet.add(url);
		urls.put(group, urlSet);
	}
	
	public static String getUrls(String group){
		Set<String> urlSet = new HashSet<>();
		if(urls.containsKey(group)){
			urlSet = urls.get(group);
		}
		StringBuffer str = new StringBuffer();
		for(String key: urlSet){
			str.append(key).append(";");
		}
		return str.toString().endsWith(";")?str.toString().substring(0,str.toString().length() - 1):str.toString();
	}
}
