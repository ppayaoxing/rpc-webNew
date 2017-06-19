package com.rpc.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务端处理 
 * @author Administrator
 */
public interface ServerProtocolProcessor {

	public void exec(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public String getClazzName();
}
