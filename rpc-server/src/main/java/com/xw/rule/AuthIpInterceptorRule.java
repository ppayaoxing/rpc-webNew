package com.xw.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rpc.rule.InterceptorRule;
import com.rpc.rule.Order;


/**
 * 测试ip验证 
 * @author Administrator
 */
public class AuthIpInterceptorRule implements Order, InterceptorRule{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	@Override
	public int getOrderLevel() {
		return 10;
	}
	
}
