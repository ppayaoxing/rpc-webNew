package com.admin.web.impl;

import com.admin.common.ServerInfoData;
import com.admin.utils.AdminCacheUtils;
import com.admin.web.AdminService;

public class AdminServiceImpl implements AdminService {

	@Override
	public void Register(String ip, String port, String webApp) {
		AdminCacheUtils.addServer(new ServerInfoData(ip, port, webApp, true, System.currentTimeMillis()/1000).build());
	}

	@Override
	public void hitHeat(String serverId,long time) {
		ServerInfoData info =AdminCacheUtils.getServerInfo(serverId);
		info.hitHeart(time);
	}

	@Override
	public String getUrls(String group) {
		return AdminCacheUtils.getUrls(group);
	}
	
}
