package com.admin.web;

public interface AdminService {

	public void Register(String ip,String port,String webApp,String group);
	public void hitHeat(String serverId);
	public String getUrls(String group);
}
