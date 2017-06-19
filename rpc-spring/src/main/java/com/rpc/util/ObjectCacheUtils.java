package com.rpc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.rpc.rule.InterceptorRule;
import com.rpc.server.ServerProtocolProcessor;

public class ObjectCacheUtils {

	private static ConcurrentHashMap<String, Vector<String>> objectCache = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, ServerProtocolProcessor> map = new ConcurrentHashMap<String, ServerProtocolProcessor>(500);
	
	private static List<InterceptorRule> rules = new ArrayList<InterceptorRule>();
	
	public static void put(String clazzName,String clusterKey){
		Vector<String> vector = objectCache.get(clazzName);
		if(vector == null)
			vector = new Vector<String>();
		if(!vector.contains(clusterKey))
			vector.add(clusterKey);
		objectCache.put(clazzName, vector);
	}
	
	public static boolean containClusterKey(String clazzName,String clusterKey){
		String name = clusterKey.substring(0,clusterKey.indexOf("_"));
		ServerProtocolProcessor processor = map.get(name);
		if(!map.containsKey(name))
			return false;
		Vector<String> vector = objectCache.get(processor.getClazzName());
		clusterKey = processor.getClazzName() + "." + clusterKey.substring(clusterKey.indexOf("_")+1);
		if(vector != null && vector.contains(clusterKey)){
			return true;
		}
		return false;
	}
	
	public static void addHandler(String uri,ServerProtocolProcessor skeleton) throws Exception{
		if(map.containsKey(uri)){
			throw new Exception(uri+" serverProcess already exsits");
		}
		map.put(uri, skeleton);		
	}
	
	public static void removeHandler(String uri){
		if(map.containsKey(uri))
			map.remove(uri);
	}
	public static ServerProtocolProcessor get(String uri){
		if(map.containsKey(uri))
			return map.get(uri);
		return null;
	}

	public static void setRules(List<InterceptorRule> ruls){
		ObjectCacheUtils.rules = ruls;
	}
	public static List<InterceptorRule> getRules(){
		return rules;
	}
	
	public static String getParameterNames(Class<?>[] types){
		StringBuffer str = new StringBuffer();
		for(Class<?> clazz : types){
			str.append(clazz.getSimpleName()).append(",");
		}
		String text = str.toString();
		if(text.endsWith(",")){
			text = text.substring(0,text.length()-1);
		}
		return text;
	}
}
