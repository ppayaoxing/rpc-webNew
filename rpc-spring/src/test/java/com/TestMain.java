package com;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

public class TestMain {

/*	public static void main(String[] args) throws IllegalArgumentException, ClassNotFoundException {
		UserService userService = (UserService)Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), 
				new Class[]{Class.forName("com.test.rpc.UserService")}, new HessianHandler());
		userService.addUser("jaco");
//		System.out.println(object);
	}*/
	
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
//		    HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
//		    hessianProxyFactory.setOverloadEnabled(true);
//	        hessianProxyFactory.setConnectTimeout(10000);
//	        hessianProxyFactory.setReadTimeout(10000);
//	        UserService user = (UserService) hessianProxyFactory.create(Class.forName("com.test.rpc.UserService"), "http://192.168.0.73:8080/rpc-server/com.test.rpc.UserService",
//	        		Thread.currentThread().getContextClassLoader());
//	        user.addUser("jaco");
		String sql ="select * from abc where id=1";
		String sql1 = getSlq(1);
		System.out.println(sql==sql1);
		DecimalFormat df2 = new DecimalFormat("###.00");
		double db = new Double(2.918);
		int i1 = (int)db;
		System.out.println(df2.format(db));
		
	}

	private static String getSlq(int i) {
			String sql = "select * from abc where id="+i;
		return sql.intern();
	}
}
