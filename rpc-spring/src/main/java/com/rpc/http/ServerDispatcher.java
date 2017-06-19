package com.rpc.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.server.ServerProtocolProcessor;
import com.rpc.util.DispatcherUtils;
import com.rpc.util.ObjectCacheUtils;

/**
 * rpc 控制中心
 * @author Administrator
 */
public class ServerDispatcher extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = -5240824779593394246L;
	private static Logger logger = LoggerFactory.getLogger(ServerDispatcher.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = DispatcherUtils.getInterface(req);
		ServerProtocolProcessor processor = ObjectCacheUtils.get(uri);
		try {
			if(processor != null)
				processor.exec(req, resp);
		} catch (Exception e) {
			logger.error("",e);
		}
	}


	
}
