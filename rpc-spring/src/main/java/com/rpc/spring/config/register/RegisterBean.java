package com.rpc.spring.config.register;

/**
 * 注册bean 
 */
public class RegisterBean {

	private String ip;
	private String port;
	private String webApp;
	private String group;
	
	private String adminUrl;
	
	public String getAdminUrl() {
		if(adminUrl != null && !adminUrl.endsWith("/server"))
			return adminUrl+"/server";
		return adminUrl;
	}
	public String getIp() {
		return ip;
	}
	public String getPort() {
		return port;
	}
	public String getWebApp() {
		return webApp;
	}
	public RegisterBean(String ip, String port, String webApp,String group,String adminUrl) {
		super();
		this.ip = ip;
		this.port = port;
		this.webApp = webApp;
		this.group = group;
		this.adminUrl = adminUrl;
	}
	
	public String getGroup() {
		return group;
	}
	
	public String getId(){
		return ip+":"+port+"/"+webApp;
	}
}
