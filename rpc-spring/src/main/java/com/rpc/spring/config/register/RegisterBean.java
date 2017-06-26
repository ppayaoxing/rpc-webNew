package com.rpc.spring.config.register;

public class RegisterBean {

	private String ip;
	private String port;
	private String webApp;
	
	public String getIp() {
		return ip;
	}
	public String getPort() {
		return port;
	}
	public String getWebApp() {
		return webApp;
	}
	public RegisterBean(String ip, String port, String webApp) {
		super();
		this.ip = ip;
		this.port = port;
		this.webApp = webApp;
	}
	
	public String getUrl(){
		return "http://"+ip+":"+port+"/"+webApp;
	}
	
	public String getId(){
		return ip+":"+port+"/"+webApp;
	}
}
