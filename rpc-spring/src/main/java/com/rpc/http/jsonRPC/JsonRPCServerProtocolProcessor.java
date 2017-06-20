package com.rpc.http.jsonRPC;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MethodInvoker;

import com.rpc.factory.protocol.jsonrpc.Result;
import com.rpc.http.ServerProtocolProcessor;
import com.rpc.rule.InterceptorRule;
import com.rpc.util.ObjectCacheUtils;
import com.rpc.util.RequestUtils;
import com.yzmy.jsonrpc4j.JsonRpcMultiServer;

/**
 *  jsonRpc
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
		try {
			if (!isUse) {
				response.setStatus(410);
				return;
			}
			if (!perHandler(request, response)) {
				return;
			}
			if (isDowngrade && ObjectCacheUtils.containClusterKey(this.clazzName, request.getHeader("clusterKey"))) {
				if (downgradeProcessor != null) {
					deal(request, response.getWriter());
					return;
				}
				logger.warn("downgradeProcessor 引用对象不存在");
				return;
			}
			jsonRpcMultiServer.handle(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			afterHandler(request, response);
		}
	}

	
	/**
	 * 后置拦截
	 * @param request
	 * @param response
	 */
	private void afterHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				rule.afterCompletion(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 前置拦截
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean perHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				if (!rule.preHandle(request, response))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 降级处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void deal(HttpServletRequest request, PrintWriter print) throws Exception {
		Object[] value = RequestUtils.getObjects(request);
		MethodInvoker methodInvoker = new MethodInvoker();
		methodInvoker.setTargetObject(downgradeProcessor);
		methodInvoker.setTargetMethod(request.getHeader("methodName"));
		methodInvoker.setArguments(value);
		methodInvoker.prepare();
		Object object = methodInvoker.invoke();
		Result<Object> result = new Result<>();
		result.setResult(object);
		print.print( new ObjectMapper().writeValueAsString(result));
	}

	@Override
	public String getClazzName() {
		return clazzName;
	}

}
