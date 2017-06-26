package com.admin.web;

public interface AdminService {

	public void Register(String ip,String port,String webApp);
	public void hitHeat(String serverId,long time);
	public String getUrls(String group);
}
