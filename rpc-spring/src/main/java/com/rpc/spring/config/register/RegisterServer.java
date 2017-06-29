package com.rpc.spring.config.register;

import com.rpc.factory.ParamsData;
import com.rpc.util.RequestUtils;

/**
 * 注册服务 
 */
public class RegisterServer {
	
	private RegisterBean register;
	private RegisterServer(){}
	
	private static class InnerClass{
		private static RegisterServer INSTANCE = new RegisterServer();
	}
	
	public static RegisterServer getRegisterServer(){
		return InnerClass.INSTANCE;
	}

	public RegisterBean getRegister() {
		return register;
	}

	public RegisterServer setRegister(RegisterBean register) {
		this.register = register;
		return this;
	}
	
	public void register(){
		try{
			RequestUtils.send(register.getAdminUrl(), new ParamsData()
				.setClazz("com.admin.web.AdminService")
				.setMethodName("Register")
				.setParameterTypes(new Class<?>[]{String.class,String.class,String.class,String.class})
				.setValues(new Object[]{register.getIp(),register.getPort(),register.getWebApp(),register.getGroup()})
				.setTimeout(3000));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void hitHeart(){
		try {
			RequestUtils.send(register.getAdminUrl(), new ParamsData()
					.setClazz("com.admin.web.AdminService")
					.setMethodName("hitHeat")
					.setParameterTypes(new Class<?>[]{String.class,Long.class})
					.setValues(new Object[]{register.getId()})
					.setTimeout(2000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUrls(String group){
		String urls = null;
		try {
			urls = (String) RequestUtils.send(register.getAdminUrl(), new ParamsData()
					.setClazz("com.admin.web.AdminService")
					.setMethodName("getUrls")
					.setParameterTypes(new Class<?>[]{String.class})
					.setValues(new Object[]{group})
					.setTimeout(2000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
	}
	
	public static void main(String[] args) {
		try{
			RegisterBean register = new RegisterBean("192.168.1.5", "80", "server","server","http://192.168.1.5:8080/rpc-admin");
//			RequestUtils.send(register.getAdminUrl(), new ParamsData()
//					.setClazz("com.admin.web.AdminService")
//					.setMethodName("Register")
//					.setParameterTypes(new Class<?>[]{String.class,String.class,String.class,String.class})
//					.setValues(new Object[]{"192.168.1.5","80","server","server"})
//					.setTimeout(3000));
			RegisterServer.getRegisterServer().setRegister(register);
			
			RegisterServer.getRegisterServer().register();
			
			RegisterServer.getRegisterServer().hitHeart();
			
			String url = RegisterServer.getRegisterServer().getUrls("server");
			System.out.println(url);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
