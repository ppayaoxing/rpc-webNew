package com.rpc.http;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianSkeleton;

/**
 * rpc 控制中心
 * @author Administrator
 */
public class HessianDispatcher extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = -5240824779593394246L;
	private static Logger logger = LoggerFactory.getLogger(HessianDispatcher.class);
	
	private static ConcurrentHashMap<String, HessianSkeleton> map = new ConcurrentHashMap<String, HessianSkeleton>(500);
	
	public static void addHandler(String uri,HessianSkeleton skeleton) throws Exception{
		if(map.containsKey(uri)){
			throw new Exception(uri+" handler already exsits");
		}
		map.put(uri, skeleton);		
	}
	
	public static void removeHandler(String uri){
		if(map.containsKey(uri))
			map.remove(uri);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		resp.setStatus(500,"不支持get请求");
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if(uri.indexOf("/")!=-1)
			uri = uri.substring(uri.lastIndexOf("/")+1);
		if(map.containsKey(uri)){
			HessianSkeleton skeleton = map.get(uri);
			try {
				skeleton.invoke(req.getInputStream(), resp.getOutputStream());
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		logger.error(uri+" not exsits");
//		resp.setStatus(500,"服务不存在");
	}
}
