package com.rpc.server.jsonRPC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.server.ServerProtocolProcessor;
import com.yzmy.jsonrpc4j.JsonRpcMultiServer;

/**
 *  jsonRpc
 * @author Administrator
 */
public class JsonRPCServerProtocolProcessor implements ServerProtocolProcessor {

	private static Logger logger = LoggerFactory.getLogger(JsonRPCServerProtocolProcessor.class);

	private JsonRpcMultiServer jsonRpcMultiServer;
	private boolean isDowngrade;// 是否降级 true:是；false：否
	private boolean isUse;// 是否可用
	private Object downgradeProcessor;
	private String clazzName;

	public JsonRPCServerProtocolProcessor(JsonRpcMultiServer jsonRpcMultiServer, boolean isDowngrade, boolean isUse,
			Object downgradeProcessor, String clazzName) {
		super();
		this.jsonRpcMultiServer = jsonRpcMultiServer;
		this.isDowngrade = isDowngrade;
		this.isUse = isUse;
		this.downgradeProcessor = downgradeProcessor;
		this.clazzName = clazzName;
	}

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 jsonRpcMultiServer.handle(request, response);
	}

	@Override
	public String getClazzName() {
		return clazzName;
	}

}
