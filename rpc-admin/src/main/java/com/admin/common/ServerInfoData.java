package com.admin.common;

import java.util.Collections;
import java.util.Vector;

/**
 * 
 * @author Administrator
 *
 */
public class ServerInfoData {
	
	private String ip;
	private String port;
	private String webApp;
	private Vector<Long> aliveList = new Vector<>();  
	private boolean alive;//false:死了;true:活的
	private long lasteHitTime;//最后一次心跳时间 (秒)
	
	public ServerInfoData() {
		super();
	}
	
	public ServerInfoData build(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				long lastTime = System.currentTimeMillis()/1000;//秒
				while( true ){
					if(System.currentTimeMillis()/1000 - lastTime > 3){
						if(System.currentTimeMillis()/1000 - getLasteHitTime() >60*10){
							setAlive(false);
						}
					}else{
						try {
							Thread.sleep(1000*2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}).start();
		return this;
	}
	
	public ServerInfoData(String ip, String port, String webApp,  boolean alive,
			long lasteHitTime) {
		super();
		this.ip = ip;
		this.port = port;
		this.webApp = webApp;
		this.alive = alive;
		this.lasteHitTime = lasteHitTime;
	}
	public String getIp() {
		return ip;
	}
	public ServerInfoData setIp(String ip) {
		this.ip = ip;
		return this;
	}
	public String getPort() {
		return port;
	}
	public ServerInfoData setPort(String port) {
		this.port = port;
		return this;
	}
	public String getWebApp() {
		return webApp;
	}
	public ServerInfoData setWebApp(String webApp) {
		this.webApp = webApp;
		return this;
	}
	public Vector<Long> getAliveList() {
		return aliveList;
	}
	public boolean isAlive() {
		return alive;
	}
	public ServerInfoData setAlive(boolean alive) {
		this.alive = alive;
		return this;
	}
	public long getLasteHitTime() {
		return lasteHitTime;
	}
	
	public void hitHeart(long lastTime){
		this.lasteHitTime = lastTime;
		aliveList.add(lastTime);
		Collections.sort(aliveList);
		if(aliveList.size() > 1000){
			aliveList = (Vector<Long>) aliveList.subList(0, 1000);
		}
	}
	
	public String getId(){
		return ip+":"+port+"/"+webApp;
	}
	

}
