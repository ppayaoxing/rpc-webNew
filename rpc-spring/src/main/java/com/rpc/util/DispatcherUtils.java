package com.rpc.util;

import javax.servlet.http.HttpServletRequest;

public class DispatcherUtils {

	public static String getInterface(HttpServletRequest req) {
		String uri = req.getRequestURI();
		if(uri.indexOf("/")!=-1){
			int lastIndex = uri.indexOf("?");
			if(lastIndex > 0)
				uri = uri.substring(uri.lastIndexOf("/")+1,lastIndex);
			else
				uri = uri.substring(uri.lastIndexOf("/")+1);
		}
		return uri;
	}
}
