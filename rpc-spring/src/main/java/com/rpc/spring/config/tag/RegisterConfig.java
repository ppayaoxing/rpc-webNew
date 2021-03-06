package com.rpc.spring.config.tag;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;

import com.rpc.spring.config.register.RegisterBean;
import com.rpc.spring.config.register.RegisterServer;

public class RegisterConfig implements InitializingBean {

	private String adminUrl;
	
	private String id;//id	
	private String ip;
	private String webApp;//
	private String port;
	private String group;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getWebApp() {
		return webApp;
	}

	public void setWebApp(String webApp) {
		this.webApp = webApp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		RegisterServer.getRegisterServer().setRegister(new RegisterBean(ip, port, webApp,group,adminUrl)).register();
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);
		schedule.scheduleAtFixedRate(new Runnable() {//立即执行，并且每过两秒 发送一次心跳
			@Override
			public void run() {
				RegisterServer.getRegisterServer().hitHeart();
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
	
//	public static void main(String[] args) {
//		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);
//		schedule.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				RegisterServer.getRegisterServer().hitHeart();
//			}
//		}, 0, 2, TimeUnit.SECONDS);
//	}

}
