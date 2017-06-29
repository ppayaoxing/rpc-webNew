package com.admin.web.impl;

import com.admin.common.ServerInfoData;
import com.admin.utils.AdminCacheUtils;
import com.admin.web.AdminService;

public class AdminServiceImpl implements AdminService {

	/**
	 * 注册
	 */
	@Override
	public void Register(String ip, String port, String webApp,String group) {
		String serverId = ip+":"+port+"/"+webApp;//id
		ServerInfoData data = AdminCacheUtils.getServerInfo(serverId);
		long lastTime = System.currentTimeMillis()/1000;
		if(data == null){
			data = new ServerInfoData(ip, port, webApp, true, lastTime);
			data.build();
		}	
		data.hitHeart(lastTime);
		AdminCacheUtils.addServer(data);
		AdminCacheUtils.addUrl(group, data.getUrl());
	}

	/**
	 * 心跳
	 */
	@Override
	public void hitHeat(String serverId) {
		ServerInfoData info =AdminCacheUtils.getServerInfo(serverId);
		if(info == null)
			return ;
		long lastTime = System.currentTimeMillis()/1000;
		info.hitHeart(lastTime);
	}

	/**
	 * 获取url
	 */
	@Override
	public String getUrls(String group) {
		return AdminCacheUtils.getUrls(group);
	}
	
}
